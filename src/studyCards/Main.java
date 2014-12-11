package studyCards;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
//		JFrame mainFrame = new JFrame("StudyCards");
		
		CardCollection test = new CardCollection("Test collection");

		System.out.println(test.getName());
		test.addCard(new Card("Testfråga 1", "Testsvar 1"));
		test.addCard(new Card("Testfråga 2", "Testsvar 2"));

		
		try {
			System.out.println("Question 1: " +test.getCard(0).getQuestion() + " Answer: "+ test.getCard(0).getAnswer());
			System.out.println("Question 2: " +test.getCard(1).getQuestion() + " Answer: "+ test.getCard(1).getAnswer());
			System.out.println("Question 3: " +test.getCard(2).getQuestion() + " Answer: "+ test.getCard(2).getAnswer());
		} catch (CardNotFoundException e) {
			System.out.println(e);
		}
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
		
		CardCollection test2 = new CardCollection();
		test2.addCard(new Card("Testfråga 3", "Testsvar 3"));
		test2.addCard(new Card("Testfråga 4", "Testsvar 4"));
		
		ArrayList<CardCollection> testList = new ArrayList<CardCollection>();
		
		Utilities.saveCollection(test);
		CardCollection test3 = Utilities.loadCollection(test.getName());
		
		try {
			System.out.println("Question 1: " +test3.getCard(0).getQuestion() + " Answer: "+ test3.getCard(0).getAnswer());
			System.out.println("Question 2: " +test3.getCard(1).getQuestion() + " Answer: "+ test3.getCard(1).getAnswer());
			System.out.println("Question 3: " +test3.getCard(2).getQuestion() + " Answer: "+ test3.getCard(2).getAnswer());
		} catch (CardNotFoundException e) {
			System.out.println(e);
	}
		testList.add(test);

		testList.add(test3);
		testList.add(test2);
		Utilities.saveCollectionList(testList);
		
		ArrayList<CardCollection> testList2 = Utilities.loadCollectionList();
		
		
		for (int i = 0; i < testList.size();i++){
			System.out.println(testList.get(i).getName());
		}
		
		for (int i = 0; i < testList2.size();i++){
			System.out.println(testList2.get(i).getName());
		}
	}

}
