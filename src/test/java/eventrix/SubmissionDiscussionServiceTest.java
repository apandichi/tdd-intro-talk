package eventrix;

import eventrix.domain.*;
import notification.SubmissionDiscussionService;
import notification.NotificationService;
import notification.SubscribersService;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SubmissionDiscussionServiceTest {

    Organizer organizer = new Organizer("organizer");
    Reviewer reviewer = new Reviewer("reviewer");
    Speaker speaker = new Speaker("speaker");

    NotificationService notificationService = mock(NotificationService.class);
    SubscribersService subscribersService = mock(SubscribersService.class);
    SubmissionDiscussionService submissionDiscussionService = new SubmissionDiscussionService(notificationService, subscribersService);

    @Test
    public void notify_users_when_a_comment_is_posted() {
        SubmissionDiscussion discussion = new SubmissionDiscussion(speaker, organizer, asList(reviewer));
        Comment comment = new Comment(speaker, "first comment", discussion);
        List<User> subscribers = asList(organizer, reviewer, speaker);

        when(subscribersService.getSubscribers(comment)).thenReturn(subscribers);
        submissionDiscussionService.postComment(comment, discussion);

        assertEquals(discussion.getNumberOfComments(), 1);
        verify(notificationService).notifyUsers(subscribers);
    }
}
