package us.analytiq.knime.qvx.reader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import org.knime.core.data.DataTableSpec;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.core.node.util.CheckUtils;
import org.knime.core.util.FileUtil;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;

/**
 * This is the model implementation of Qvx.
 * Qvx Node
 *
 * @author Monica
 */
public class QvxReaderNodeModel extends NodeModel {
        
	public static final String DEFAULT_PATH = "";

	public static final String CFGKEY_FILE_PATH = "FilePath";

    private final SettingsModelString filepath = new SettingsModelString(CFGKEY_FILE_PATH, DEFAULT_PATH);

    protected QvxReaderNodeModel() {
    	super(0, 1);
    }

    @Override
    protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
            final ExecutionContext exec) throws Exception {
    	
        QvxFileReaderNodeSettings settings = new QvxFileReaderNodeSettings();

        CheckUtils.checkSourceFile(filepath.getStringValue());
        URL url = FileUtil.toURL(filepath.getStringValue());

        settings.setDataFileLocationAndUpdateTableName(url);
        settings.setQvxReader(new QvxReader(url, exec));
        return settings.getQvxReader().getTableData();
    }

    @Override
    protected void reset() {
    	// No additional action needs to be taken on reset
    }

    @Override
    protected DataTableSpec[] configure(final DataTableSpec[] inSpecs)
            throws InvalidSettingsException {
    	
        return new DataTableSpec[]{null};
    }


    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {
        
    	filepath.saveSettingsTo(settings);
    }

    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
            throws InvalidSettingsException {
    	
        filepath.loadSettingsFrom(settings);
    }

    @Override
    protected void validateSettings(final NodeSettingsRO settings)
            throws InvalidSettingsException {
    	
        filepath.validateSettings(settings);
    }
    
    @Override
    protected void loadInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
    	
    	/* Everything handed to output ports is loaded automatically.
    	 * No additional action is necessary.
    	 */
    }
    
    @Override
    protected void saveInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
    	
    	/* Everything written to output ports is saved automatically.
    	 * No additional action is necessary.
    	 */
    }
}

