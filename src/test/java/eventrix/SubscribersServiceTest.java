package eventrix;

import eventrix.domain.*;
import notification.SubscribersService;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class SubscribersServiceTest {

    Speaker speaker = new Speaker("speaker");
    Organizer organizer = new Organizer("organizer");
    Reviewer reviewerOne = new Reviewer("reviewer 1");
    Reviewer reviewerTwo = new Reviewer("reviewer 2");

    SubscribersService subscribersService = new SubscribersService();

    SubmissionDiscussion discussion = new SubmissionDiscussion(speaker, organizer, asList(reviewerOne, reviewerTwo));

    @Test
    public void organizer_posts_first_comment() {
        Comment comment = postComment(organizer);
        List<User> subscribers = subscribersService.getSubscribers(comment);
        assertEquals(asList(speaker), subscribers);
    }

    @Test
    public void reviewer_posts_first_comment() {
        Comment comment = postComment(reviewerOne);
        List<User> subscribers = subscribersService.getSubscribers(comment);
        assertEquals(asList(speaker), subscribers);
    }

    @Test
    public void speaker_posts_first_comment() {
        Comment comment = postComment(speaker);
        List<User> subscribers = subscribersService.getSubscribers(comment);
        assertEquals(asList(organizer, reviewerOne, reviewerTwo), subscribers);
    }

    @Test
    public void speaker_replies_to_organizer() {
        postComment(organizer);
        Comment comment = postComment(speaker);
        List<User> subscribers = subscribersService.getSubscribers(comment);
        assertEquals(asList(organizer), subscribers);
    }

    @Test
    public void reviewer_replies_to_speaker_and_organizer() {
        postComment(organizer);
        postComment(speaker);
        Comment comment = postComment(reviewerOne);
        List<User> subscribers = subscribersService.getSubscribers(comment);
        assertEquals(asList(organizer, speaker), subscribers);
    }

    @Test
    public void reviewer_replies_to_speaker_and_organizer_back_and_forth() {
        postComment(organizer);
        postComment(speaker);
        postComment(organizer);
        postComment(speaker);
        Comment comment = postComment(reviewerOne);
        List<User> subscribers = subscribersService.getSubscribers(comment);
        assertEquals(asList(organizer, speaker), subscribers);
    }

    @Test
    public void reviewer_and_organizer_back_and_forth() {
        postComment(organizer);
        postComment(reviewerOne);
        postComment(organizer);
        postComment(reviewerOne);
        Comment comment = postComment(organizer);
        List<User> subscribers = subscribersService.getSubscribers(comment);
        assertEquals(asList(reviewerOne, speaker), subscribers);
    }

    private Comment postComment(User author) {
        Comment comment = new Comment(author, "hello", discussion);
        discussion.addComment(comment);
        return comment;
    }

}
