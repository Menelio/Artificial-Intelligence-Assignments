package eightTile;
//Course: CS4242
//Student name: Menelio Alvarez
//Student ID: 000874829
//Assignment #: 2.2
//Due Date: September 13, 2019
//Signature: _________________
//Score: _________________
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUI extends Application {
	//position in anchor pane of root puzzle box node
	double Y = 10.0;
	double X = 585.0;
	
	//moves count
	int mc =0;
	
	//Label
	Label goal = new Label("GOAL REACHED");
	@Override
	public void start(Stage stage) throws Exception {
		//Root PuzzleBox and A* search object
		PuzzleBox rootPb =new PuzzleBox();
		AStarSearch search = new AStarSearch(rootPb);
		
		//Array of PuzzleBoxPanes for moves
		PuzzleBoxPane[] pbPane= new PuzzleBoxPane[( (rootPb.getFn()*4) +rootPb.getGn() + 1)];
		
		//Pain to display tree
		AnchorPane aPane = new AnchorPane();
		aPane.setMinSize(1500, 1000);
		
		//scrollbar
	    ScrollPane scroller = new ScrollPane(aPane);
		AnchorPane.setRightAnchor(scroller, 5.0); 
		
		//GridPane for controls and button
		GridPane control = new GridPane();
		Button gen = new Button("Genrate Puzzle");
		Button step = new Button("Step");
		control.add(gen, 0, 0);
		control.add(step, 0, 1);
		
		//gen button event
		gen.setOnAction(e->{
			PuzzleBoxPane rootPbPane= new PuzzleBoxPane(rootPb,"Root");
			AnchorPane.setTopAnchor(rootPbPane.getPuzzleBoxPane(), Y);
			AnchorPane.setLeftAnchor(rootPbPane.getPuzzleBoxPane(), X);
			aPane.getChildren().add(rootPbPane.getPuzzleBoxPane());
			Y= Y+200;
			X=10;
			for(int i=0; i < rootPb.getMoves().length;i++) {
				pbPane[mc] = new PuzzleBoxPane(rootPb.getMoves()[i],"Move"+(i+1));
				AnchorPane.setTopAnchor(pbPane[mc].getPuzzleBoxPane(), Y);
				AnchorPane.setLeftAnchor(pbPane[mc].getPuzzleBoxPane(), X);
				aPane.getChildren().add(pbPane[mc].getPuzzleBoxPane());
				mc++;
				X=X+200;
			}
		});
		
		//step button event
		step.setOnAction(e->{
		
			if(!search.getStatus()) {
				
				search.step();
				
				Y= Y+200;
				X = 585.0;
				pbPane[mc] =  new PuzzleBoxPane(search.getCurrent(),"Choosen Move");
				AnchorPane.setTopAnchor(pbPane[mc].getPuzzleBoxPane(), Y);
				AnchorPane.setLeftAnchor(pbPane[mc].getPuzzleBoxPane(), X);
				aPane.getChildren().add(pbPane[mc].getPuzzleBoxPane());
				mc++;
				
				if(!search.getStatus()) {
					Y= Y+200;
					X=10;
					search.getCurrent().genMoves();
					for(int i=0; i < search.getCurrent().getMoves().length;i++) {
						pbPane[mc] = new PuzzleBoxPane(search.getCurrent().getMoves()[i],"Move"+(i+1));
						AnchorPane.setTopAnchor(pbPane[mc].getPuzzleBoxPane(), Y);
						AnchorPane.setLeftAnchor(pbPane[mc].getPuzzleBoxPane(), X);
						aPane.getChildren().add(pbPane[mc].getPuzzleBoxPane());
						mc++;
						X=X+200;
					}
				}else {
					Y= Y+80;
					X= X+200;
					AnchorPane.setTopAnchor(goal, Y);
					AnchorPane.setLeftAnchor(goal, X);
					aPane.getChildren().add(goal);
				}
			}
			
		});
		
		
		//set up stage
		Scene scene = new Scene(new BorderPane(scroller, aPane,null,  null, control),1500, 1000);
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

}
