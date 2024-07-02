package sec02.casting;

public class Controller {
	public MemberService service;
	
	public void setService(MemberService service) {
		this.service = service;
	



/*
Controller controller = new Controller();
controller.setService();
*/
	Controller controller = new Controller();
	controller.setService(new MemberService());
	controller.service.login();

	controller.setService(new AService());
	controller.service.login();
	}
}
