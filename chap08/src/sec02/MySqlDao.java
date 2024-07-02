package sec02;

public class MySqlDao implements DataAccessObject {
	@Override
	public void select() {
		System.out.println("MySqlDao DB select");
	}
	@Override
	public void insert() {
		System.out.println("MySqlDao DB insert");
	}
	@Override
	public void update() {
		System.out.println("MySqlDao DB update");
	}
	@Override
	public void delete() {
		System.out.println("MySqlDao DB delete");

}
}
