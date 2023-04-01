package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import board.Board;
import board.Move;
import board.Tile;
import pieces.*;
import util.PieceColor;

/*
 * The Gui class is the main interactivity of the game and is what is interacted with
 */
public class GUI {

	public Board board;
	public JFrame gameFrame;
	public JPanel boardPanel;
	JMenuBar menu;
	public PromotionPanel promotionPanel;

	//Variables used when making the visuals
	final Dimension FRAME_SIZE = new Dimension(800,800);
	final Dimension TILE_SIZE = new Dimension(100,100);
	Color darkTileColor = Color.decode("#593E1A");
	Color lightTileColor = Color.decode("#FFFACD");
	String defaultPieceImagesPath = "resources/pixel/";

	//variables that we use when interacted with the board
	public Tile sourceTile;
	public Tile destinationTile;
	public Piece selectedPiece;

	public Move movedActivePawn;
	public Pawn activePawn;
	public int selectedPromotion;
	public boolean restart = false;
	public boolean startPromotion = false;


	/*
	 * constructor creating the frame and adding the components
	 */
	public GUI(Board board){
		//setting frame components
		this.gameFrame = new JFrame("Checkmate Champions");
		this.gameFrame.setLayout(new BorderLayout());
		this.gameFrame.setSize(FRAME_SIZE);
		this.board = board;

		//creating and adding the board panel
		this.boardPanel = new BoardPanel();
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

		//creating and adding menu bear
		this.menu = createTableMenuBar();
		this.gameFrame.setJMenuBar(menu);
		this.gameFrame.setLocationRelativeTo(null);
		this.gameFrame.setVisible(true);
	}
	


	/*
	 * Board panel is the list of all the separate tile panels to display as one
	 */
	public class BoardPanel extends JPanel{
		public final List<TilePanel> boardTiles;

		/*
		 * constructor creates and adds all the tile panels
		 */
		BoardPanel(){
			super(new GridLayout(8,8));
			this.boardTiles = new ArrayList<>();
			for(int i = 0; i < Board.NUM_TILES; i++) {
				TilePanel tilePanel = new TilePanel(this, i);
				this.boardTiles.add(tilePanel);
				add(tilePanel);
			}
			setPreferredSize(FRAME_SIZE);
			validate();
		}

		/*
		 * draw board is going through each tile and adding it to the board panel
		 */
		public void drawBoard(Board board) {
			removeAll();
			for(TilePanel tilePanel : boardTiles) {
				tilePanel.drawTile(board);
				add(tilePanel);
			}
			validate();
			repaint();
		}
	}
	
	public void restartGame() {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				board = new Board();
				((BoardPanel) boardPanel).drawBoard(board);
			}
		});
	}
	

	/*
	 * called to create the JMenu bar and add its components
	 */
	public JMenuBar createTableMenuBar() {
		JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		return tableMenuBar;
	}

	/*
	 * creates the file option to have drop downs in it
	 */
	public JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("File");

		//create the restart item
		//TODO not functioning
		JMenuItem restartMenuItem = new JMenuItem("Restart Game");
		restartMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restartGame();
			}
		});
		fileMenu.add(restartMenuItem);

		//creates the save item
		//TODO not functioning
		JMenuItem saveMenuItem = new JMenuItem("Save Game");
		saveMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//save game
			}
		});
		fileMenu.add(saveMenuItem);

		//creates the exit item
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitMenuItem);
		

		return fileMenu;
	}


	/*
	 * tile panel is its own separate panel that makes each tile able to interact with
	 */
	public class TilePanel extends JPanel{
		public int tileId;

		//creates the panel with their id
		public TilePanel(BoardPanel boardPanel, int tileId) {
			super(new GridBagLayout());
			this.tileId = tileId;
			setPreferredSize(TILE_SIZE);

			//calls and adds the tiles color and piece icon
			assignTileColor();
			assignTilePieceIcon(board);
			Border blackline = BorderFactory.createLineBorder(Color.black,2);
			this.setBorder(blackline);


			//TODO The whole mouse listener as an inner class I was struggling to get it to work as a seperate class
			addMouseListener(new MouseListener() {
				
			

				@Override
				public void mouseClicked(MouseEvent e) {
					//if right is clicked
					if(SwingUtilities.isRightMouseButton(e)) {
						System.out.println("Right click");
						sourceTile = null;
						selectedPiece = null;
						destinationTile = null;
						//if left is clicked
					}else if(SwingUtilities.isLeftMouseButton(e)) {
						
						if(sourceTile == null) {
							activePawn = null;
							//sets sourceTile as the tile id clicked on
							sourceTile = board.getTile(tileId);
							//sets selected piece as the piece on that tile
							selectedPiece = sourceTile.getPiece();
							

							if(selectedPiece.toString().equals("P"))
							{
								System.out.println("PAWN SELECETD!");
								activePawn = (Pawn)selectedPiece;

							}

							if(selectedPiece == null || selectedPiece.getPieceColor() != board.getCurrentPlayer().getTeam()) {
								sourceTile = null;
								selectedPiece = null;
								activePawn = null;
								System.out.println("No piece selected");
							}
						}else {
							destinationTile = board.getTile(tileId);

							if(destinationTile != null) {
								for(Move move : pieceLegalMoves(board)) {
									if(destinationTile.getTileCoord() == move.getDestinationCoordinate()) {
										//making move
										board = move.makeMove(board, selectedPiece, destinationTile.getTileCoord(), destinationTile.getPiece());

										
										if(activePawn != null) {
											if(activePawn.eigthRankEdgeCheck(destinationTile.getTileCoord()) || activePawn.firstRankEdgeCheck(destinationTile.getTileCoord()))
											{
												System.out.println("FINISH LINE");
												movedActivePawn = move;
												PromotionFrame pf = new PromotionFrame(activePawn.getPieceColor(), boardPanel);
											
											//break;
											}		 
										}


										destinationTile = null;
										sourceTile = null;
										selectedPiece = null;
										break;
									}
								}
							}
							if(destinationTile != null) {
								if(destinationTile.getPiece() == null) {
									System.out.println("cant move here");
								}else if(destinationTile.getPiece().getPieceColor() == sourceTile.getPiece().getPieceColor()) {
									activePawn = null;
									sourceTile = destinationTile;
									selectedPiece = sourceTile.getPiece();
									destinationTile = null;
								}
							}
						}
					}
					
					// redraws board when clicked on
					SwingUtilities.invokeLater(new Runnable(){				
						@Override
						public void run() {
							boardPanel.drawBoard(board);
						}
					});
					checkWinningConditions();
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

			});
			validate();
		}
		


//
//		public boolean checkWin(Board board)
//		{
//			for(Piece piece : board.getActivePieces())
//
//
//			if(board.getActivePieces(board.gameBoard).contains(Piece King));
//		}



		/*
		 * draw tile assigns the color and icon
		 * highlights legal moves
		 * and repains it
		 */
		public void drawTile(Board board) {
			assignTileColor();
			assignTilePieceIcon(board);
			highlightLegals(board);
			validate();
			repaint();
		}

		/*
		 * using each pieces to string method to get the piece and then grab the image icon to display
		 */
		private void assignTilePieceIcon(Board board) {
			this.removeAll();
			if(board.getTile(this.tileId).isTileOccupied()) {
				try {
					Image image = ImageIO.read(new File(defaultPieceImagesPath + board.getTile(this.tileId).getPiece()
							.getPieceColor().toString().substring(0,1)
							+ board.getTile(this.tileId).getPiece().toString() + ".gif"));
					image = image.getScaledInstance(80, 80, image.SCALE_DEFAULT);
					add(new JLabel(new ImageIcon(image)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		/*
		 * this gets the boards legal moves and the tile id to draw green cross on legal moves
		 * If the type of move is a legal move, it will draw the possible move tiles in green
		 * if the type of move is an attack move, it will draw the tiles in red
		 */
		public void highlightLegals(Board board) {
			for(Move move : pieceLegalMoves(board)) {
				if(move.getDestinationCoordinate() == this.tileId) {
					if(move.getClass() == Move.NormalMove.class) {
						this.setBackground(new Color(78,237,86,100));
					}else if(move.getClass() == Move.AttackMove.class) {
						this.setBackground(new Color(237,78,78,100));
					}
				}
			}
		}

		/*
		 * grabs the selected boards legal moves
		 */
		public Collection<Move> pieceLegalMoves(Board board){
			if(selectedPiece != null) {
				return selectedPiece.calculateMoves(board);
			}
			return Collections.emptyList();
		}


		/*
		 * runs through each tile id and assigns it with a color to draw the tile panel
		 */
		public void assignTileColor() {
			if(Board.EIGTH_RANK[this.tileId] ||
					Board.SIXTH_RANK[this.tileId] ||
					Board.FOURTH_RANK[this.tileId] ||
					Board.SECOND_RANK[this.tileId]) {
				setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
			}else if(Board.SEVENTH_RANK[this.tileId] ||
					Board.FIFTH_RANK[this.tileId] ||
					Board.THIRD_RANK[this.tileId] ||
					Board.FIRST_RANK[this.tileId]) {
				setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
			}
		}

	}
	
	
	
	public void checkWinningConditions() {
		if(board.blackPlayer().isInCheckMate()) {
			displayMessage("White wins");
			restartGame();
		}else if(board.whitePlayer().isInCheckMate()) {
			displayMessage("Black wins");
			restartGame();
		}
	}
	
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	
	
	public void getNewPromotionPiece(int decision, BoardPanel boardPanel) {
		//JOptionPane.showMessageDialog(null, "Pawn needs to be promoted. Please select which piece you'd like");
		//int decision = Integer.parseInt(JOptionPane.showInputDialog("Please select: "));
		
		if(decision == 0)
		{
			Queen newQueen = new Queen(activePawn.getPiecePos(),activePawn.getPieceColor());
			board = movedActivePawn.makeMove(board, newQueen, activePawn.getPiecePos(), null);
		}

		else if(decision == 1)
		{
			Rook newRook = new Rook(activePawn.getPiecePos(),activePawn.getPieceColor());
			board = movedActivePawn.makeMove(board, newRook, activePawn.getPiecePos(), null);
		}

		else if (decision == 2)
		{
			Knight newKnight = new Knight(activePawn.getPiecePos(),activePawn.getPieceColor());
			board = movedActivePawn.makeMove(board, newKnight, activePawn.getPiecePos(), null);
		}

		else if (decision == 3)
		{
			Bishop newBishop = new Bishop(activePawn.getPiecePos(),activePawn.getPieceColor());
			board = movedActivePawn.makeMove(board, newBishop, activePawn.getPiecePos(), null);
		}
		
		boardPanel.drawBoard(board);
		checkWinningConditions();
		activePawn = null;
	}
	
	
	/*
	public void createPromotionFrame(PieceColor color) {
		Dimension PROMOTION_FRAME_SIZE = new Dimension(200,200);
		JFrame frame = new JFrame("Promotion");
		frame.setLayout(new BorderLayout());
		frame.setSize(PROMOTION_FRAME_SIZE);

		this.promotionPanel = new PromotionPanel(frame);
		frame.add(promotionPanel, BorderLayout.CENTER);
		frame.setVisible(true);
		
		
	}
	*/
	
	
	public class PromotionFrame extends JFrame {
		Dimension PROMOTION_FRAME_SIZE = new Dimension(200,200);
		PromotionFrame(PieceColor colorToSet, BoardPanel boardPanel){
			this.setLayout(new BorderLayout());
			this.setSize(PROMOTION_FRAME_SIZE);
			
			this.add(new PromotionPanel(this, boardPanel, colorToSet), BorderLayout.CENTER);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
		}
		
	}
	
	
	
	public class PromotionPanel extends JPanel{
		public List<PromotionTilePanel> promotionTiles;
		Dimension PROMOTION_FRAME_SIZE = new Dimension(200,200);
		JFrame frame;
		
		PromotionPanel(JFrame frame, BoardPanel boardPanel, PieceColor colorToSet){
			super(new GridLayout(2,2));
			this.frame = frame;
			this.promotionTiles = new ArrayList<>();
			for(int i = 0; i < 4; i++) {
				PromotionTilePanel tilePanel = new PromotionTilePanel(this, i, boardPanel, colorToSet);
				this.promotionTiles.add(tilePanel);
				add(tilePanel);
			}
			setPreferredSize(PROMOTION_FRAME_SIZE);
			validate();
		}
	}
	
	
	
	
	/*
	 * used for the new frame for promotion window
	 */
	public class PromotionTilePanel extends JPanel{
		public int tileId;
		public PieceColor colorToSet;

		//creates the panel with their id
		public PromotionTilePanel(PromotionPanel promotionPanel, int tileId, BoardPanel boardPanel, PieceColor colorToSet) {
			super(new GridBagLayout());
			this.tileId = tileId;
			this.colorToSet = colorToSet;
			setPreferredSize(TILE_SIZE);

			//assignTileColor();
			//calls and adds the tiles color and piece icon
			assignPromotionTilePieceIcon(colorToSet, tileId);
			assignTileColor();
			Border blackline = BorderFactory.createLineBorder(Color.black,2);
			this.setBorder(blackline);

			//TODO The whole mouse listener as an inner class I was struggling to get it to work as a seperate class
			addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
				
					if(SwingUtilities.isLeftMouseButton(e)) {
						promotionPanel.frame.dispose();
						getNewPromotionPiece(tileId, boardPanel);
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				
			}); 
			
		}
		
		
		/*
		 * draw tile assigns the color and icon
		 * highlights legal moves
		 * and repains it
		 */
		public void drawTile() {
			assignPromotionTilePieceIcon(colorToSet, tileId);
			assignTileColor();
			validate();
			repaint();
		}

		/*
		 * using each pieces to string method to get the piece and then grab the image icon to display
		 */
		private void assignPromotionTilePieceIcon(PieceColor colorToSet, int tileId) {
			String piecePath = "";
			switch(tileId) {
			case 0: piecePath = "Q.gif";
			break;
			case 1: piecePath = "R.gif";
			break;
			case 2: piecePath = "N.gif";
			break;
			case 3: piecePath = "B.gif";
			}
			System.out.println(tileId + piecePath);
			this.removeAll();
			try {
				Image image = ImageIO.read(new File(defaultPieceImagesPath + colorToSet.toString().substring(0,1) + piecePath));
				image = image.getScaledInstance(80, 80, image.SCALE_DEFAULT);
				add(new JLabel(new ImageIcon(image)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		
		
		public void assignTileColor() {
			if(tileId == 0 || tileId == 3) {
				setBackground(Color.DARK_GRAY);
			}else  {
				setBackground(Color.LIGHT_GRAY);
			}
		}
	}
}