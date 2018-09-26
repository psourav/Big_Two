import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**The BigTwoTable class implements the CardGameTable interface. 
 * It is used to build a GUI for the Big Two card game and handle all user actions. 
 * 
 * @author souravpadhiari
 *
 */

public class BigTwoTable implements CardGameTable {
	private BigTwoClient game;
	private boolean[] selected;
	private int activePlayer;
	private JFrame frame;
	private JPanel bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private JTextArea msgArea=new JTextArea();
	private JTextArea msgAreaB=new JTextArea();
	private Image[][] cardImages;
	private Image cardBackImage;
	private Image[] avatars;

	private JTextField write;

	
	
	/**The constructor for creating a BigTwoTable. 
	 * The parameter game is a reference to a card game associates with this table.

	 * @param game which is executed. 
	 */
	public BigTwoTable(BigTwoClient game) {
		this.game = game;
		selected = new boolean[13];
		resetSelected();
		setActivePlayer(game.getCurrentIdx());
		
		avatars = new Image[4];
																
		avatars[0] = new ImageIcon("images/p1.png").getImage();				//Loading the Images (Putting them in their respective arrays)
		avatars[1] = new ImageIcon("images/p2.png").getImage();
		avatars[2] = new ImageIcon("images/p3.png").getImage();
		avatars[3] = new ImageIcon("images/p4.png").getImage();
		cardBackImage = new ImageIcon("images/back_card.jpg").getImage();
		
		
		char ci_suit[] = { 'd', 'c', 'h', 's'};
		cardImages = new Image[4][13];
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<13; j++) {
				cardImages[i][j]=new ImageIcon("images/"+(j+1) + ci_suit[i] +".gif").getImage();			
			}
		}
		
		
		frame = new JFrame("BIG TWO GAME");						//Make new frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		
		JSplitPane split = new JSplitPane();			//Splitting the frame
		split.setDividerLocation(1000);
		split.setDividerSize(2);
		
		
		JMenuBar menuBar = new JMenuBar();				//Putting the Menu in the Frame
		JMenu menu=  new JMenu("Menu");
		JMenuItem quit= new JMenuItem("Quit");
		quit.addActionListener(new QuitMenuItemListener());
		JMenuItem restart= new JMenuItem("Connect");
		restart.addActionListener(new ConnectMenuItemListener());
		menu.add(quit);
		menu.add(restart);
		menuBar.add(menu);
		JMenu mess = new JMenu("Message");
		JMenuItem clrchat = new JMenuItem("Clear");
		clrchat.addActionListener(new ClearChatListener());
		mess.add(clrchat);
		menuBar.add(mess);
		frame.setJMenuBar(menuBar);
		
		write=new JTextField("Press Enter to Send Message",60);
		write.addKeyListener(new EnterListener());
		write.setMaximumSize(new Dimension(400,50));
		
		
		JPanel right_panel=new JPanel();
		right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.Y_AXIS));
		
		msgArea = new JTextArea();								//Creating the Text Area
		msgArea.setEditable(false);
		msgArea.setBackground(new Color(224, 241, 238));
		msgArea.setFont(new Font("Helvetica",Font.PLAIN, 18 ));
		JScrollPane scroll = new JScrollPane(msgArea);   
		msgArea.setLineWrap(true); 
		scroll.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		msgAreaB=new JTextArea();
		
		msgAreaB.setEditable(false);
		msgAreaB.setText("Chat Box:\n");
		msgAreaB.setBackground(Color.WHITE);msgAreaB.setFont(new Font("Helvetica",Font.PLAIN, 18 ));
		msgAreaB.setLineWrap(true); JScrollPane scroll2 = new JScrollPane(msgAreaB); 
		scroll2.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
		scroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		right_panel.add(scroll);
		right_panel.add(scroll2);
		right_panel.add(write);
		
		split.setRightComponent(right_panel);
		
		
		bigTwoPanel = new BigTwoPanel();					//New BigTwoPanel
		bigTwoPanel.setMinimumSize(new Dimension(1000,840));
		
		
		bigTwoPanel.setLayout(new BorderLayout());			//Setting Layout 
		JPanel buttonPanel = new JPanel();
															//Putting the bottom Panel for PLAY & PASS
		playButton = new JButton("Play");
		passButton = new JButton("Pass");
		playButton.addActionListener(new PlayButtonListener());
		buttonPanel.add(playButton);
		passButton.addActionListener(new PassButtonListener());
		buttonPanel.setBackground(Color.DARK_GRAY);
		buttonPanel.add(passButton);
		bigTwoPanel.add(buttonPanel, BorderLayout.PAGE_END);
		
		
		split.setLeftComponent(bigTwoPanel);
		
		frame.add(split,BorderLayout.CENTER);
		frame.setSize(1500, 840);
		frame.setVisible(true);
		
	}
	
	
	/**
	 * The method for setting the index of the active player (i.e., the current player).
	 */
	public void setActivePlayer(int activePlayer) {
		this.activePlayer=activePlayer;
	}
	
	/**The method for getting an array of indices of the cards selected.
	 * @return Integer Array Select of Card Indices 
	 */
	public int[] getSelected() {
		int counter=0;
		int []iselect=null;
		for(int i=0; i<this.selected.length; i++) {
			if (this.selected[i]==true) {
				counter++;
			}
		}
		int j=0;
		if (counter!=0) {							
			iselect=new int[counter];
			
			for(int i=0; i<this.selected.length;i++) {			//Finding all the True Values and Returning Indices
				if (this.selected[i]==true) {
					iselect[j]=i;
					j++;
				}
			}
		}
		return iselect;
	}
	/**
	 * The method for resetting the list of selected cards.
	 */
	public void resetSelected() {
		selected=new boolean[13];
		for(int i=0;i<13;i++)
			selected[i]=false;
	}
	/**
	 *The method for repainting the GUI.
	 */
	public void repaint() {
		frame.repaint();
	}
	/**The method for printing the specified string to the message area of the GUI.
	 * @param String Value Which Has to be printed on the Text Area 
	 */
	public void printMsg(String msg) {
		msgArea.append(msg);
		msgArea.setCaretPosition(msgArea.getText().length());
		repaint();
	}
	public void printChat(String msg) {
		msgAreaB.append(msg);
		msgAreaB.setCaretPosition(msgAreaB.getText().length());
		repaint();
		
	}
	/**
	 *The method for clearing the message area of the GUI.
	 */
	public void clearMsgArea() {
		msgArea.setText(null);
	}
	/**The method for resetting the GUI. 
	 * 
	 */
	public void reset() {
	
		this.resetSelected();
		this.clearMsgArea();
		this.enable();
	}
	/**
	 * The method for enabling user interactions with the GUI.
	 */
	public void enable() {

		playButton.setEnabled(true);
		
		passButton.setEnabled(true);
		
		bigTwoPanel.setEnabled(true);
		
	}
	
	/**
	 *The method for disabling user interactions with the GUI.
	 */
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);
	}
	/**An inner class that extends the JPanel class and implements the MouseListener interface. 
	 * Overrides the paintComponent() method inherited from the JPanel class to draw the card game table. 
	 * Implements the mouseClicked() method from the MouseListener interface to handle mouse click events.
	 * @author souravpadhiari
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public BigTwoPanel() {
			addMouseListener(this);
			
		}
		
		public void paintComponent(Graphics g) {		//Draw in BigTwo Panel
			
			g.setFont(new Font("TimesRoman",Font.BOLD,24));
			setActivePlayer(game.getCurrentIdx());
			if(activePlayer==0)
				g.setColor(Color.BLUE);
			g.drawString(game.getPlayerList().get(0).getName(), 10, 20);
			g.setColor(Color.BLACK);	
			g.drawImage(avatars[0], 10, 30, this);
		    g.drawLine(0, 130, 1500, 130);
			if(activePlayer==1)
				g.setColor(Color.BLUE);
			g.drawString(game.getPlayerList().get(1).getName(), 10, 150);
			g.setColor(Color.BLACK);
		    g.drawImage(avatars[1], 10, 160, this);
		    g.drawLine(0,260, 1500, 260);
		    if(activePlayer==2)
		    	g.setColor(Color.BLUE);
		    g.drawString(game.getPlayerList().get(2).getName(), 10, 280);
		    g.setColor(Color.BLACK);
		    g.drawImage(avatars[2], 10, 290, this);
		    g.drawLine(0, 390, 1500, 390);
		    if(activePlayer==3)
		    	g.setColor(Color.BLUE);
		    g.drawString(game.getPlayerList().get(3).getName(), 10, 410);
		    g.setColor(Color.BLACK);
		    g.drawImage(avatars[3], 10, 420, this); 
		    g.drawLine(0, 520, 1500, 520);
		    
		    
		    if (game.getPlayerID() == 0) {				//Checking ActivePlayer and Displaying the Cards accordingly. //front image
			    	for (int i = 0; i < game.getPlayerList().get(0).getNumOfCards(); i++) {
			    		if (selected[i]==false)
			    			g.drawImage(cardImages[game.getPlayerList().get(0).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(0).getCardsInHand().getCard(i).getRank()], 200+30*i, 30, this);//Idle
			    		else
			    			g.drawImage(cardImages[game.getPlayerList().get(0).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(0).getCardsInHand().getCard(i).getRank()], 200+30*i, 30-20, this);//Lifted
			    		
			    		
			    		
			    		
			    		
			    	}
		    } else {
			    	for (int i = 0; i < game.getPlayerList().get(0).getCardsInHand().size(); i++) {
			    		g.drawImage(cardBackImage, 200 + 30*i, 30, this);//Back Image
			    	}
			    	

		    }
		    if (game.getPlayerID() == 1) {
			    	for (int i = 0; i < game.getPlayerList().get(1).getNumOfCards(); i++) {
			    		if (selected[i]==false)
			    			g.drawImage(cardImages[game.getPlayerList().get(1).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(1).getCardsInHand().getCard(i).getRank()], 200+30*i, 160, this);
			    		else
			    			g.drawImage(cardImages[game.getPlayerList().get(1).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(1).getCardsInHand().getCard(i).getRank()], 200+30*i, 160-20, this);
			    		
			    		
			    	}
		    } else {
			    	for (int i = 0; i < game.getPlayerList().get(1).getCardsInHand().size(); i++) {
			    		g.drawImage(cardBackImage, 200 + 30*i, 160, this);
			    	}
			    	
		    }
		    if (game.getPlayerID() == 2) {
			    	for (int i = 0; i < game.getPlayerList().get(2).getNumOfCards(); i++) {
			    		if (selected[i]==false)
			    			g.drawImage(cardImages[game.getPlayerList().get(2).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(2).getCardsInHand().getCard(i).getRank()], 200+30*i, 290, this);
			    		else
			    			g.drawImage(cardImages[game.getPlayerList().get(2).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(2).getCardsInHand().getCard(i).getRank()], 200+30*i, 290-20, this);
			    		
			    		
			    	}
		    } else {
			    	for (int i = 0; i < game.getPlayerList().get(2).getCardsInHand().size(); i++) {
			    		g.drawImage(cardBackImage, 200 + 30*i, 290, this);
			    	}
			    	
		    }
		    if (game.getPlayerID() == 3) {
			    	for (int i = 0; i < game.getPlayerList().get(3).getNumOfCards(); i++) {
			    		if (selected[i]==false)
			    			g.drawImage(cardImages[game.getPlayerList().get(3).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(3).getCardsInHand().getCard(i).getRank()], 200+30*i, 420, this);
			    		else
			    			g.drawImage(cardImages[game.getPlayerList().get(3).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(3).getCardsInHand().getCard(i).getRank()], 200+30*i, 420-20, this);
			    		
			    		
			    	}
		    } else {
			    	for (int i = 0; i < game.getPlayerList().get(3).getCardsInHand().size(); i++) {
			    		g.drawImage(cardBackImage, 200 + 30*i, 420, this);
			    	}
		    }
		    
		    
		    
		    g.setColor(Color.RED);//Color for Hands On Table
		    g.drawString("HANDS ON TABLE", 10, 540);
		    if (game.getHandsOnTable()!=null) {
			    if (game.getHandsOnTable().size() == 0) {
			    		g.setColor(Color.BLACK);
			    		g.setFont(new Font("TimesRoman",Font.PLAIN,22));
			    		g.drawString("No Hand on Table ----------------> Let The GAME Begin :-D", 30, 670);
			    }
			    else {
			    		Hand CardsOnTable = game.getHandsOnTable().get(game.getHandsOnTable().size()-1);
			    		for (int i = 0; i < CardsOnTable.size(); i++)
			    			g.drawImage(cardImages[CardsOnTable.getCard(i).getSuit()][CardsOnTable.getCard(i).getRank()], 200 + 93*i, 550, this);		//Displaying Cards on Table at specified Pixel
			    		g.setColor(Color.BLUE);
			    									//Displaying the owner of the hand on table
			    			g.drawString("--------->Played by " + game.getPlayerList().get(game.getLast()).getName(), 700, 600);
			    		
			    }
		    }
		    
		    
		}
		public void mouseClicked(MouseEvent e) {			//Mouse Click in BigTwoPanel
			int mx=e.getX();
			int my=e.getY();
			int csa=game.getPlayerList().get(activePlayer).getNumOfCards()-1;
			boolean idK =false;
			
			if(my>=(30+(130*activePlayer)-20) && my <= (30+(130*activePlayer)-20+97)){			//For Double Click 
				if(mx>=(200+(30*csa))&&mx<=(200+(30*csa)+73)) {
					if(selected[csa])
						selected[csa]=false;
				}else {
					for(int i=0; i<csa; i++ ) {
						if(mx>=(200+(30*i))&&mx<=(200+(30*i)+30)) {
							if(selected[i]) {
								selected[i]=false;
								idK =true;
							}
						}
					}
				}
			}
			if(!idK) {
				if(my>=(30+(130*activePlayer)) && my <= (30+(130*activePlayer)+97) ) {		//For Single Click 
					if(mx>=(200+(30*csa))&&mx<=(200+(30*csa)+73)) {
						if(!selected[csa])
							selected[csa]=true; 
					}else {
						for(int i=0; i<csa; i++ ) {
							if(mx>=(200+(30*i))&&mx<=(200+(30*i)+30)) {
								if(!selected[i])
									selected[i]=true;
							}
						}
					}
				}
			}
			
			
		
			
			frame.repaint();
			
			
			
			
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
	}
	
	
	
	
	
	/**An inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the Play button. 
	 * 
	 * @author souravpadhiari
	 *
	 */
	class EnterListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				game.sendMessage(new CardGameMessage(CardGameMessage.MSG,-1, write.getText()));
				write.setText("");
				
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		
		
	}
	class PlayButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
				int moves[] = getSelected();
				if (moves != null)
					game.makeMove(game.getCurrentIdx(), moves);
				
				resetSelected();
				frame.repaint();
			}
		
		
	}
	/**An inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the Pass button. 
	 * @author souravpadhiari
	 *
	 */
	class PassButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			int moves[] = getSelected();
			if (moves == null)
				game.makeMove(game.getCurrentIdx(), moves);			//Making a Move
			resetSelected();
			frame.repaint();
		}
	}
	class ClearChatListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			msgAreaB.setText("Chat Box: \n");
			
		}
		
	}
	/** An inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the Connect menu item.
	 * @author souravpadhiari
	 *
	 */
	class ConnectMenuItemListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			
			if (game.getPlayerID() == -1) {
				game.makeConnection();
			} else if (game.getPlayerID() >= 0 && game.getPlayerID() <= 3)
				printMsg("You are already connected!!!\n");
		}
			
	}
		
		
	}
	/**An inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the Quit menu item.
	 * @author souravpadhiari
	 *
	 */
	class QuitMenuItemListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			
		}
		
	}
	
