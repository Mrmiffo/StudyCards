package studyCards;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
//		JFrame mainFrame = new JFrame("StudyCards");
		
		CardCollection test = new CardCollection("Test collection");
		System.out.println(test.getName());
		test.addCard(new Card("Testfråga 1", "Testsvar 1"));
		test.addCard(new Card("Testfråga 2", "Testsvar 2"));
		
//		try {
//			System.out.println("Question 1: " +test.getCard(0).getQuestion() + " Answer: "+ test.getCard(0).getAnswer());
//			System.out.println("Question 2: " +test.getCard(1).getQuestion() + " Answer: "+ test.getCard(1).getAnswer());
//			System.out.println("Question 3: " +test.getCard(2).getQuestion() + " Answer: "+ test.getCard(2).getAnswer());
//		} catch (CardNotFoundException e) {
//			System.out.println(e);
//		}
//		
//		try {
//			test.removeCard(3);
//		} catch (CardNotFoundException e1) {
//			System.out.println(e1);
//		}
//
//		try {
//			System.out.println("Question 1: " +test.getCard(0).getQuestion() + " Answer: "+ test.getCard(0).getAnswer());
//			System.out.println("Question 2: " +test.getCard(1).getQuestion() + " Answer: "+ test.getCard(1).getAnswer());
//			System.out.println("Question 3: " +test.getCard(2).getQuestion() + " Answer: "+ test.getCard(2).getAnswer());
//		} catch (CardNotFoundException e) {
//			System.out.println(e);
//		}
		
		Utilities.saveCollection(test);
		CardCollection test2 = Utilities.loadCollection(test.getName());
		
		try {
		System.out.println("Question 1: " +test2.getCard(0).getQuestion() + " Answer: "+ test2.getCard(0).getAnswer());
		System.out.println("Question 2: " +test2.getCard(1).getQuestion() + " Answer: "+ test2.getCard(1).getAnswer());
		System.out.println("Question 3: " +test2.getCard(2).getQuestion() + " Answer: "+ test2.getCard(2).getAnswer());
	} catch (CardNotFoundException e) {
		System.out.println(e);
	}
	}

}
