package player;

import java.util.Collection;

import board.Board;
import board.Move;
import pieces.Piece;
import util.PieceColor;

/*
 * class for white player
 */
public class WhitePlayer extends Player{

	public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMoves, Collection<Move> blackStandardLegalMoves) {
		super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}

	@Override
	public Player getOpponent() {
		return this.board.blackPlayer();
	}

	@Override
	public PieceColor getTeam() {
		return PieceColor.WHITE;
	}
}
