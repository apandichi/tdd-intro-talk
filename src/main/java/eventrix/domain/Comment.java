package eventrix.domain;

public class Comment {

    private String text;
    private User author;
    private SubmissionDiscussion submissionDiscussion;

    public Comment(User author, String text, SubmissionDiscussion submissionDiscussion) {
        this.author = author;
        this.text = text;
        this.submissionDiscussion = submissionDiscussion;
    }

    public SubmissionDiscussion getSubmissionDiscussion() {
        return submissionDiscussion;
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }
}
