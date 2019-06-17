package beans;

public class WordsBean {
    //中英文标记
    private boolean isChinese;
    //要翻译的单词，可以是中文；
    //key为中文时的翻译
    private String fy;
    private String key;
    //英音发音
    private String psE;
    //英音发音的mp3地址
    private String pronE;
    //美音发音
    private String psA;
    //美音发音的mp3地址
    private String pronA;
    //单词的词性与词义
    private String posAcceptation;
    //例句
    private String sent;

    public WordsBean() {
        this.key = "";
        this.psE = "";
        this.fy="";
        this.pronE = "";
        this.psA = "";
        this.pronA = "";
        this.posAcceptation = "";
        this.sent = "";
        this.isChinese = false;
    }

    public WordsBean(boolean isChinese, String key, String fy, String psE,
                 String pronE, String psA, String pronA, String posAcceptation, String sent) {
        this.isChinese = isChinese;
        this.fy=fy;
        this.key = key;
        this.psE = psE;
        this.pronE = pronE;
        this.psA = psA;
        this.pronA = pronA;
        this.posAcceptation = posAcceptation;
        this.sent = sent;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getPsE() {
        return psE;
    }

    public void setPsE(String psE) {
        this.psE = psE;
    }

    public String getPronE() {
        return pronE;
    }

    public void setPronE(String pronE) {
        this.pronE = pronE;
    }

    public String getPsA() {
        return psA;
    }

    public void setPsA(String psA) {
        this.psA = psA;
    }

    public String getPronA() {
        return pronA;
    }

    public void setPronA(String pronA) {
        this.pronA = pronA;
    }

    public String getPosAcceptation() {
        return posAcceptation;
    }

    public void setPosAcceptation(String posAcceptation) {
        this.posAcceptation = posAcceptation;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public boolean getIsChinese() {
        return isChinese;
    }

    public void setIsChinese(boolean isChinese) {
        this.isChinese=isChinese;
    }

    public String getFy() {
        return fy;
    }

    public void setFy(String fy) {
        this.fy=fy;
    }


        }

