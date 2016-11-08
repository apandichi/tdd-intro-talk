package notification;

import eventrix.domain.Comment;
import eventrix.domain.SubmissionDiscussion;
import eventrix.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class SubscribersService {

    class Pair {
        Function<Comment, List<User>> function;
        Supplier<Boolean> supplier;

        public Pair(Function<Comment, List<User>> function, Supplier<Boolean> supplier) {
            this.function = function;
            this.supplier = supplier;
        }
    }

    public List<User> getSubscribers(Comment comment) {
        SubmissionDiscussion submissionDiscussion = comment.getSubmissionDiscussion();
        boolean speakerIsTheAuthor = comment.getAuthor() == submissionDiscussion.getSpeaker();
        boolean isTheFirstComment = submissionDiscussion.getNumberOfComments() == 1;

        return computeSubscribers(comment, speakerIsTheAuthor, isTheFirstComment);
    }

    private List<User> computeSubscribers(Comment comment, boolean speakerIsTheAuthor, boolean isTheFirstComment) {
        Function<Comment, List<User>> first = (Comment c) -> subscribersForFirstCommentWithSpeakerAuthor(c);
        Supplier<Boolean> firstCondition = () -> isTheFirstComment && speakerIsTheAuthor;
        Pair firstPair = new Pair(first, firstCondition);


        Function<Comment, List<User>> second = (Comment c) -> subscribersForFirstCommentAndNonSpeakerAuthor(c);
        Supplier<Boolean> secondCondition = () -> isTheFirstComment && !speakerIsTheAuthor;
        Pair secondPair = new Pair(second, secondCondition);

        Function<Comment, List<User>> third = (Comment c) -> subscribersForListOfComments(c);
        Supplier<Boolean> thirdCondition = () -> !isTheFirstComment;
        Pair thirdPair = new Pair(third, thirdCondition);

        List<Pair> pairList = asList(firstPair, secondPair, thirdPair);
        return pairList.stream()
                .filter(pair -> pair.supplier.get())
                .map(pair -> pair.function.apply(comment))
                .collect(toList())
                .get(0);
    }

    private List<User> subscribersForFirstCommentWithSpeakerAuthor(Comment comment) {
        ArrayList<User> subscribers = new ArrayList<>();
        SubmissionDiscussion submissionDiscussion = comment.getSubmissionDiscussion();
        subscribers.add(submissionDiscussion.getOrganizer());
        subscribers.addAll(submissionDiscussion.getReviewers());
        return subscribers;
    }

    private List<User> subscribersForFirstCommentAndNonSpeakerAuthor(Comment comment) {
        ArrayList<User> subscribers = new ArrayList<>();
        SubmissionDiscussion submissionDiscussion = comment.getSubmissionDiscussion();
        subscribers.add(submissionDiscussion.getSpeaker());
        return subscribers;
    }

    private List<User> subscribersForListOfComments(Comment comment) {
        SubmissionDiscussion submissionDiscussion = comment.getSubmissionDiscussion();
        List<User> userList = submissionDiscussion.getComments().stream()
                .map(Comment::getAuthor)
                .collect(toList());
        userList.add(submissionDiscussion.getSpeaker());
        return userList.stream()
                .filter(user -> comment.getAuthor() != user)
                .distinct()
                .collect(toList());
    }
}
