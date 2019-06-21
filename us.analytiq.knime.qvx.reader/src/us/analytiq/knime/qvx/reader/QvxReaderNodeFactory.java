package us.analytiq.knime.qvx.reader;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "Qvx Reader" Node.
 * Qvx Reader Node
 *
 * @author Monica
 */
public class QvxReaderNodeFactory 
        extends NodeFactory<QvxReaderNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public QvxReaderNodeModel createNodeModel() {
        return new QvxReaderNodeModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrNodeViews() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeView<QvxReaderNodeModel> createNodeView(final int viewIndex,
            final QvxReaderNodeModel nodeModel) {

    	return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDialog() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeDialogPane createNodeDialogPane() {
        return new QvxReaderNodeDialog();
    }

}

