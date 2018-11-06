public class Loader {
    public static void main(String[] args)
    {
        int dimaAge = 55;
        int mishaAge = 18;
        int vasyaAge = 25;


        int oldest = dimaAge;
        int youngest = mishaAge;
        int middle = vasyaAge;

        oldest = (dimaAge <= mishaAge) ? mishaAge: dimaAge;
        oldest = (oldest <= vasyaAge) ? vasyaAge: oldest;
        youngest = (middle <= youngest) ? middle: youngest;
        youngest = (dimaAge <= youngest) ? dimaAge: youngest;
        middle = ((oldest > middle) && (youngest < middle)) ? middle: mishaAge;
        middle = ((oldest > middle) && (youngest < middle)) ? middle: dimaAge;
        System.out.println("Most old:" + oldest);
        System.out.println("Most young:" + youngest);
        System.out.println("Middle:" + middle);

    }
}
