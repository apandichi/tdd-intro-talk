package eventrix.domain;

import java.util.ArrayList;
import java.util.List;

public class SubmissionDiscussion {

    private final Speaker speaker;
    private final Organizer organizer;
    private final List<Reviewer> reviewers;

    private List<Comment> comments = new ArrayList<>();

    public SubmissionDiscussion(Speaker speaker, Organizer organizer, List<Reviewer> reviewers) {
        this.speaker = speaker;
        this.organizer = organizer;
        this.reviewers = reviewers;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public List<Reviewer> getReviewers() {
        return reviewers;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public int getNumberOfComments() {
        return comments.size();
    }
}
