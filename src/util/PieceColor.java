package util;

/*
 * enum to assign to each piece to determine its color
 */
public enum PieceColor {

	//for white pieces
	WHITE{
		//sets to negative one so it moves up the board
		@Override
		public int getDirection() {
			return -1;
		}
		@Override
		public boolean isWhite() {
			return true;
		}
		@Override
		public boolean isBlack() {
			return false;
		}
	},
	//for black pieces
	BLACK{
		//set to positive one so it moves down the board
		@Override
		public int getDirection() {
			return 1;
		}
		@Override
		public boolean isWhite() {
			return false;
		}
		@Override
		public boolean isBlack() {
			return true;
		}
	};
	
	
	public abstract int getDirection();
	public abstract boolean isBlack();
	public abstract boolean isWhite();
}
