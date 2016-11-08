package notification;

import eventrix.domain.Comment;
import eventrix.domain.SubmissionDiscussion;
import eventrix.domain.User;

import java.util.List;

public class SubmissionDiscussionService {

    private NotificationService notificationService;
    private SubscribersService subscribersService;

    public SubmissionDiscussionService(NotificationService notificationService, SubscribersService subscribersService) {
        this.notificationService = notificationService;
        this.subscribersService = subscribersService;
    }

    public void postComment(Comment comment, SubmissionDiscussion discussion) {
        discussion.addComment(comment);
        List<User> users = subscribersService.getSubscribers(comment);
        notificationService.notifyUsers(users);
    }
}
