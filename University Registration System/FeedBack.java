public class FeedBack<T> {
    
    private String name;
    private T feedback;

    public FeedBack(String name, T feedback){
        this.name = name;
        this.feedback = feedback;
    }

    @Override
    public String toString(){
        return "Feedback from " + name + " " + feedback.toString();
    }

    public String getName(){
        return name;
    }

    public T getFeedback(){
        return feedback;
    }
}
