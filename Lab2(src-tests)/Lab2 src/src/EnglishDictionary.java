import java.util.ArrayList;

public class EnglishDictionary {
    private PerfectHashing<String> dic;

    public EnglishDictionary(String s) {
        if ("linear".equals(s))
            dic = new PerfectHashingNMethod<String>();
        else
            dic = new PerfectHashingNSquareMethod<String>();
    }

    public int[] insert(String s) {
        int[] result = new int[2];
        if(dic.insert(s))
            result[0]= 1;
        else 
            result[0]= 0;
        result[1]=dic.getNumberOfRehashing();
        return result;
    }

    public boolean search(String s) {
        return dic.search(s);
    }

    public int[] delete(String s) {
        int[] result = new int[2];
        if(dic.delete(s))
            result[0]=1;
        else
            result[0]=0;
        result[1]=dic.getNumberOfRehashing();
        return result;
    }

    public int[] batchInsertFromFile(String[] arr) {
        int[] result = new int[3];
        result[1]= dic.batchInsert(arr);
        result[0]=arr.length-result[1];
        result[2]=dic.getNumberOfRehashing();
        return result;
    }

    public int[] batchDeleteFromFile(ArrayList<String> list) {
        int[] result = new int[3];
        for (String s : list) {
            if (dic.delete(s))
                result[0]++;
            else
                result[1]++;
        }
        result[2]=dic.getNumberOfRehashing();
        return result;
    }

}
