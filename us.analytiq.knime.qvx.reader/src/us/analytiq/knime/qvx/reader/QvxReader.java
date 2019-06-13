package us.analytiq.knime.qvx.reader;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.ExecutionContext;

import us.analytiq.knime.qvx.jaxb.QvxTableHeader.Fields.QvxFieldHeader;
import us.analytiq.knime.qvx.jaxb.QvxTableHeader;

public class QvxReader {

	private QvxTableHeader qvxTableHeader = null;
	private BufferedDataTable[] qvxTableData = null;
	private QvxBinaryReader qvxBinaryReader = null;
	
	public QvxReader(URL url, final ExecutionContext exec) throws Exception  {
		
	    qvxBinaryReader = new QvxBinaryReader();
	    qvxTableData = qvxBinaryReader.readQvx(url, exec);    
	}
	
	public BufferedDataTable[] getTableData() {
		return qvxTableData;
	}
	

	public int getNumColumns() {
		QvxTableHeader.Fields fields =  qvxTableHeader.getFields(); 
		List<QvxFieldHeader> qvxFieldHeaderList = fields.getQvxFieldHeader();
		return qvxFieldHeaderList.size();		
	}
}
