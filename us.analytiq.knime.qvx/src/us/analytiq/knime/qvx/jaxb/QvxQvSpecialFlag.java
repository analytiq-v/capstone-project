package us.analytiq.knime.qvx.jaxb;

public enum QvxQvSpecialFlag {

	QVX_QV_SPECIAL_NULL((byte)0),
	QVX_QV_SPECIAL_INT((byte)1),
	QVX_QV_SPECIAL_DOUBLE((byte)2),
	QVX_QV_SPECIAL_STRING((byte)4),
	QVX_QV_SPECIAL_INT_AND_STRING((byte)5),
	QVX_QV_SPECIAL_DOUBLE_AND_STRING((byte)6);
	
	private final byte value;
	
	private QvxQvSpecialFlag(byte theValue) {
		value = theValue;
	}
	
	public byte getValue() {
		return value;
	}
}
