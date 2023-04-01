package board;


import java.util.HashMap;
import java.util.Map;
import pieces.Piece;

/*
 * This class is to make an object for reach tile that is represented on the board
 */
public abstract class Tile {

	int tileCoord;
	static Map<Integer, EmptyTile> emptyTiles = createAllEmptyTiles();
	
	/*
	 * constructor
	 */
	public Tile(int tileCoord){
		this.tileCoord = tileCoord;
	}
	
	/*
	 * create a map of 64 empty tiles
	 */
	public static Map<Integer, EmptyTile> createAllEmptyTiles(){
		 
		Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		
		for(int i = 0; i < Board.NUM_TILES; i++) {
			emptyTileMap.put(i,  new EmptyTile(i));
		}
		
		return emptyTileMap;
	}
	
	/*
	 * create a tile with the respected piece on it
	 * or grab an empty tile
	 */
	public static Tile createTile(int tileCoord, Piece piece) {
		
		if(piece != null) {
			return new OccupiedTile(tileCoord, piece);
		}else {
			return emptyTiles.get(tileCoord);
		}
	}
	
	/*
	 * getter for the tile coordinate
	 */
	public int getTileCoord() {
		return this.tileCoord;
	}
	
	/*
	 * abstract methods for inner classes
	 */
	public abstract boolean isTileOccupied();
	public abstract Piece getPiece();
	
	
	/*
	 * class to represent empty tile in the map
	 */
	public static class EmptyTile extends Tile{
		
		public EmptyTile(int coordinate){
			super(coordinate);
		}
		
		@Override
		public boolean isTileOccupied() {
			return false;
		}
		
		@Override
		public Piece getPiece() {
			return null;
		}
	}
	
	
	/*
	 * class to represent occupied tile in the map
	 */
	public static class OccupiedTile extends Tile{
		
		Piece pieceOnTile;
		
		private OccupiedTile(int tileCoord, Piece pieceOnTile){
			super(tileCoord);
			this.pieceOnTile = pieceOnTile;
		}
		
		@Override
		public boolean isTileOccupied() {
			return true;
		}
		
		@Override
		public Piece getPiece() {
			return this.pieceOnTile;
		}
	}
}
