package entities;

public enum WertigkeitE {

	ASS(11), KOENIG(4), DAME(3), BUBE(2), ZEHN(10);

	private int numVal;

	WertigkeitE(int numVal) {
		this.numVal = numVal;
	}

	public static String[] getValues() {
		return new String[] { WertigkeitE.ASS.name(), WertigkeitE.KOENIG.name(), WertigkeitE.DAME.name(),
				WertigkeitE.BUBE.name(), WertigkeitE.ZEHN.name() };
	}

	public int getNumVal() {
		return numVal;
	}

}
