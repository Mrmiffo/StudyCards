package studyCards;

public class Card {
	private String question;
	private String answer;
	
	public Card(String question, String answer){
		this.question = question;
		this.answer = answer;
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
	
	public String getAnswer(){
		return answer;
	}

	@Override
	public int hashCode(){
		return question.hashCode() * 7;
	}
}
