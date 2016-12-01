
public class Varelager_Main {

	public static void main(String[] args) {
Varelager VL = new Varelager(10);
Vare Vare_1 = new Vare(1, "ost", 25);
Vare Vare_2 = new Vare(2, "melk", 27);
Vare Vare_3 = new Vare(3, "løk", 9);
Vare Vare_4 = new Vare(4, "kjøtt", 49);
Vare Vare_5 = new Vare(5, "rømme", 12);
VL.leggTil(Vare_1);
VL.leggTil(Vare_2);
VL.leggTil(Vare_3);
VL.leggTil(Vare_4);
VL.leggTil(Vare_5);
VL.visAlle();
VL.totalPris();
VL.slett(5);
VL.visAlle();
VL.totalPris();



	}

}
