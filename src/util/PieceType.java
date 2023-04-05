package util;

public enum PieceType {
	PAWN{
		@Override
		public boolean isKing() {
			return false;
		}
		@Override
		public boolean isPawn() {
			return true;
		}
		@Override
		public boolean isRook() {
			return false;
		}
	},
	KNIGHT{
		@Override
		public boolean isKing() {
			return false;
		}
		@Override
		public boolean isPawn() {
			return false;
		}
		@Override
		public boolean isRook() {
			return false;
		}
	},
	BISHOP{
		@Override
		public boolean isKing() {
			return false;
		}
		@Override
		public boolean isPawn() {
			return false;
		}
		@Override
		public boolean isRook() {
			return false;
		}
	},
	ROOK{
		@Override
		public boolean isKing() {
			return false;
		}
		@Override
		public boolean isPawn() {
			return false;
		}
		@Override
		public boolean isRook() {
			return true;
		}
	},
	QUEEN{
		@Override
		public boolean isKing() {
			return false;
		}
		@Override
		public boolean isPawn() {
			return false;
		}
		@Override
		public boolean isRook() {
			return false;
		}
	},
	KING{
		@Override
		public boolean isKing() {
			return true;
		}
		@Override
		public boolean isPawn() {
			return false;
		}
		@Override
		public boolean isRook() {
			return false;
		}
	};
	
	public abstract boolean isKing();
	public abstract boolean isPawn();
	public abstract boolean isRook();
}

