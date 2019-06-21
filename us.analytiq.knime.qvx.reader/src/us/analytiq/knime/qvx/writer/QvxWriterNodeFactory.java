package us.analytiq.knime.qvx.writer;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "QvxWriter" Node.
 * 
 *
 * @author 
 */
public class QvxWriterNodeFactory 
        extends NodeFactory<QvxWriterNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public QvxWriterNodeModel createNodeModel() {
        return new QvxWriterNodeModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrNodeViews() {
        return 3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeView<QvxWriterNodeModel> createNodeView(final int viewIndex,
            final QvxWriterNodeModel nodeModel) {

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
        return new QvxWriterNodeDialog();
    }

}

