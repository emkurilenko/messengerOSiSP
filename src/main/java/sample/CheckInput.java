package sample;

public  class CheckInput {
    private boolean checkSpace(String str){
        if(str.contains(" ")){
            return false;
        }else
            return true;
    }

    public static boolean checkForInputName(String name){
        if(name == null){
            return false;
        }
        if(name.length()>40)
            return false;
        for(int i = 0; i < name.length();i++){
            Character ch = name.charAt(i);
            if(!Character.isLetter(ch))
                return false;
        }
        return true;
    }

    public static String firstUpperCase(String word){
        if(word == null || word.isEmpty()) return "";
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static String deleteSpace(String str){
        return str.replaceAll(" ","");
    }
}
