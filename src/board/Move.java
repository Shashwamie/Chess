package board;

import pieces.Piece;
import player.BlackPlayer;
import player.WhitePlayer;
import util.PieceColor;

/*
 * This class is what we will call to get a certain move
 * and is constructed when a legal move is created
 */
public abstract class Move {

	Board board;
	Piece movedPiece;
	Piece attackedPiece;
	int destinationCoordinate;
	boolean isFirstMove;
	
	/*
	 * the constructor needs the board that this move is using
	 * and the destination coordinate of this being made
	 */
	public Move(Board board, int destinationCoordinate) {
		this.board = board;
		this.destinationCoordinate = destinationCoordinate;
		this.movedPiece = null;
		this.attackedPiece = null;
		this.isFirstMove = false;
	}
	
	public Move(Board board, Piece movedPiece, int destinationCoordinate) {
		this.board = board;
		this.movedPiece = movedPiece;
		this.attackedPiece = null;
		this.destinationCoordinate = destinationCoordinate;
		this.isFirstMove = movedPiece.isFirstMove();
	}
	
	public Move(Board board, Piece movedPiece, int destinationCoordinate, Piece attackedPiece) {
		this.board = board;
		this.movedPiece = movedPiece;
		this.attackedPiece = attackedPiece;
		this.destinationCoordinate = destinationCoordinate;
		this.isFirstMove = movedPiece.isFirstMove();
	}
	
	
	/*
	 * gets the destination coordinate of this move
	 */
	public int getDestinationCoordinate() {
		return this.destinationCoordinate;
	}
	public int getCurrentCoordinate() {
		return this.getMovedPiece().getPiecePos();
	}
	public Piece getMovedPiece() {
		return this.movedPiece;
	}
	public Piece getAttackedPiece() {
		return this.attackedPiece;
	}
	
	public Board makeMove(Board board, Piece movedPiece, int destinationCoordinate, Piece attackedPiece) {
		Board newBoard;
		if(movedPiece.getPieceColor() == PieceColor.WHITE) {
			newBoard = new Board(board.blackPlayer());
		}else {
			newBoard = new Board(board.whitePlayer());
		}
		for(Piece piece : board.getActivePieces(board.gameBoard)) {
			if(!this.movedPiece.equals(piece)) {
				newBoard.setPiece(piece);
			}
		}
		newBoard.setPiece(movedPiece.movePiece(this));
		//set attacked piece in grave yard
		newBoard.gameBoard = newBoard.createGameBoard();
		newBoard.whitePieces = newBoard.getWhiteActivePieces(newBoard.gameBoard);
		newBoard.blackPieces = newBoard.getBlackActivePieces(newBoard.gameBoard);
		newBoard.whitePlayer = new WhitePlayer(newBoard, newBoard.calculateLegalMoves(newBoard.whitePieces), newBoard.calculateLegalMoves(newBoard.blackPieces));
		newBoard.blackPlayer = new BlackPlayer(newBoard, newBoard.calculateLegalMoves(newBoard.blackPieces), newBoard.calculateLegalMoves(newBoard.whitePieces));
		if(movedPiece.getPieceColor() == PieceColor.WHITE) {
			newBoard.currentPlayer = newBoard.blackPlayer();
		}else {
			newBoard.currentPlayer = newBoard.whitePlayer();
		}
		return newBoard;
	}
	
	
	public static class NormalMove extends Move{

		public NormalMove(Board board, Piece movedPiece, int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		
		/*
		 * gets the destination coordinate of this move
		 */
		public int getDestinationCoordinate() {
			return this.destinationCoordinate;
		}
		public int getCurrentCoordinate() {
			return this.getMovedPiece().getPiecePos();
		}
		public Piece getMovedPiece() {
			return this.movedPiece;
		}
		public boolean isAttackMove() {
			return false;
		}
	}
	
	public static class AttackMove extends Move{

		public AttackMove(Board board, Piece movedPiece, int destinationCoordinate, Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate, attackedPiece);
		}
		
		/*
		 * gets the destination coordinate of this move
		 */
		public int getDestinationCoordinate() {
			return this.destinationCoordinate;
		}
		public int getCurrentCoordinate() {
			return this.getMovedPiece().getPiecePos();
		}
		public Piece getMovedPiece() {
			return this.movedPiece;
		}
		public boolean isAttackMove() {
			return true;
		}
	}
	
	
	public static class KingCheckMove extends Move{

		public KingCheckMove(Board board, Piece movedPiece, int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		
		/*
		 * gets the destination coordinate of this move
		 */
		public int getDestinationCoordinate() {
			return this.destinationCoordinate;
		}
		public int getCurrentCoordinate() {
			return this.getMovedPiece().getPiecePos();
		}
		public Piece getMovedPiece() {
			return this.movedPiece;
		}
		public boolean isAttackMove() {
			return false;
		}
	}
	
	
	/*
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + this.destinationCoordinate;
		result = prime * result + this.movedPiece.hashCode();
		result = prime * result + this.movedPiece.getPiecePos();
		return result;
	}
	
	@Override
	public boolean equals(final Object other) {
		if(this == other) {
			return true;
		}
		if(!(other instanceof Move)) {
			return false;
		}
		final Move otherMove = (Move) other;
		return getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
				getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
				getMovedPiece().equals(otherMove.getMovedPiece());
	}
	*/
	

}
