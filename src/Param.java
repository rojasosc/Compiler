
public class Param {
	DataType typeName;
	String paramName;
	public Param(){
	
	}
	
	public void outputParam(){
		switch(typeName){
		case INT:
			System.out.print("int ");
			break;
		case VOID:
			System.out.print("void ");
			default:
			System.out.print("undefined param type ");
		}
		System.out.print(paramName);
	}
}
