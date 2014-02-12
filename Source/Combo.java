
public class Combo {

	Attack binds[];
	Attack specialAttack;
	int nbOfBind;

	public Combo(Attack attack1, Attack attack2, Attack specAttack) {
		nbOfBind = 2;
		binds = new Attack[nbOfBind];
		binds[0] = attack1;
		binds[1] = attack2;
		specialAttack = specAttack;
	}

	public Combo(Attack attack1, Attack attack2, Attack attack3, Attack specAttack) {
		nbOfBind = 3;
		binds = new Attack[nbOfBind];
		binds[0] = attack1;
		binds[1] = attack2;
		binds[2] = attack3;
		// Les attaques passees en parametres deviennent alors des attaques declencheurs de combo
		attack1.isBind = true;
		attack2.isBind = true;
		attack3.isBind = true;
		specialAttack = specAttack;
	}
}
