package util;

public enum PieceType {
	PAWN{
		@Override
		public boolean isKing() {
			return false;
		}
	},
	KNIGHT{
		@Override
		public boolean isKing() {
			return false;
		}
	},
	BISHOP{
		@Override
		public boolean isKing() {
			return false;
		}
	},
	ROOK{
		@Override
		public boolean isKing() {
			return false;
		}
	},
	QUEEN{
		@Override
		public boolean isKing() {
			return false;
		}
	},
	KING{
		@Override
		public boolean isKing() {
			return true;
		}
	};
	
	public abstract boolean isKing();
}

