package notification;

import eventrix.domain.Comment;
import eventrix.domain.SubmissionDiscussion;
import eventrix.domain.User;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SubscribersService {

    public List<User> getSubscribers(Comment comment) {
        ArrayList<User> subscribers = new ArrayList<>();
        SubmissionDiscussion submissionDiscussion = comment.getSubmissionDiscussion();
        boolean speakerIsTheAuthor = comment.getAuthor() == submissionDiscussion.getSpeaker();
        boolean isTheFirstComment = submissionDiscussion.getNumberOfComments() == 1;
        if (isTheFirstComment) {
            if (speakerIsTheAuthor) {
                subscribers.add(submissionDiscussion.getOrganizer());
                subscribers.addAll(submissionDiscussion.getReviewers());
            } else {
                subscribers.add(submissionDiscussion.getSpeaker());
            }
        } else {
            List<User> userList = submissionDiscussion.getComments().stream()
                    .map(Comment::getAuthor)
                    .collect(toList());
            userList.add(submissionDiscussion.getSpeaker());
            return userList.stream()
                    .filter(user -> comment.getAuthor() != user)
                    .distinct()
                    .collect(toList());
        }
        return subscribers;
    }
}
