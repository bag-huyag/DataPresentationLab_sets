package dictionaryOpenHashing;

public class dictionary {
    private static class element{
        char[] name;
        element next;
        public element(char[] na, element ne){
            name = na;
            next = ne;
        }
    }

    private element array[];

    public dictionary(int a){

    }
}