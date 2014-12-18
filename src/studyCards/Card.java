package studyCards;

/**
 * The basic cards in StudyCards. Only contain 2 strings and some getter/setter methods.
 * @author Anton
 *
 */
public class Card implements Cloneable{
	private String question;
	private String answer;
	
	/**
	 * Constructor to create a simple card
	 * @param question
	 * @param answer
	 */
	public Card(String question, String answer){
		this.question = question;
		this.answer = answer;
	}
	
	/**
	 * Empty constructor used only by the Utilities when creating a new card to input the question and answer separately. May be removed by introducing a String array in the load method...
	 */
	public Card(){
		
	}
	
	@Override
	public boolean equals(Object o){
		if (this == o){
			return true;
		} else if (o == null){
			return false;
		} else if (o.getClass() == this.getClass()){
			return this.question.equals(((Card)o).getQuestion());
		}
		return false;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question){
		this.question = question;
	}
	
	public void setAnswer(String answer){
		this.answer = answer;
	}
	
	public String getAnswer(){
		return answer;
	}

	@Override
	public int hashCode(){
		return question.hashCode() * 7;
	}
	
	@Override
	public Card clone(){
		try {
			return (Card)super.clone();
		} catch (CloneNotSupportedException e) {
			return null; //Will not happen as Card implements cloneable.
		}
	}
}
