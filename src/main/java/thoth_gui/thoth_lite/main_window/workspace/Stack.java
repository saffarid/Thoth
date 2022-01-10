package thoth_gui.thoth_lite.main_window.workspace;

import javafx.scene.Node;

import java.util.HashMap;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class Stack
        extends java.util.Stack<Node>
implements Flow.Publisher<HashMap<StackType, Boolean>>{

    private StackType type;
    private SubmissionPublisher<HashMap<StackType, Boolean>> publisher;

    public Stack(StackType type) {
        super();
        publisher = new SubmissionPublisher<>();
        this.type = type;
    }

    @Override
    public Node push(Node item) {
        Node push = super.push(item);
        submit();
        return push;
    }

    private void submit() {
        HashMap<StackType, Boolean> res = new HashMap<>();
        res.put(type, empty());
        publisher.submit(res);
    }

    @Override
    public void subscribe(Flow.Subscriber<? super HashMap<StackType, Boolean>> subscriber) {
        publisher.subscribe(subscriber);
        submit();
    }
}
