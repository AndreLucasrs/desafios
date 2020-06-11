package idwall.desafio.string;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public class IdwallFormatter extends StringFormatter {


    private static final int SPACE = 32;
    private static final int NEW_LINE = 10;
    private static final int COMMA = 44;

    /**
     * Should format as described in the challenge
     *
     * @param text
     * @return
     */
    @Override
    public String format(String text) {

        text = getTextNoBreakLine(text);

        int qtdCaracteres = 40;
        String newText = "";

        newText = makeText(text, qtdCaracteres, newText);

        return newText;
    }

    @Override
    public String format(String text, Integer limit) {
        text = getTextNoBreakLine(text);

        int qtdCaracteres = limit;
        String newText = "";

        newText = makeText(text, qtdCaracteres, newText);

        return newText;
    }


    private String getTextNoBreakLine(String text) {
        return text.replace("\n", " ");
    }

    private String makeText(String text, int qtdCaracteres, String newText) {

        int count = 0;
        int start = 0;
        int end = qtdCaracteres;
        int positionCharacter = 0;

        for (char character : text.toCharArray()) {

            if (isChacarcter(character, SPACE, COMMA, NEW_LINE)) {
                positionCharacter = count;
            }

            if (end == count) {
                if (isChacarcter(character, SPACE, COMMA, NEW_LINE)) {

                    newText = getNewText(text, qtdCaracteres, start, end, newText);
                    start = end + 1;
                    end = start + qtdCaracteres;
                } else {

                    end = positionCharacter + 1;
                    newText = getNewText(text, qtdCaracteres, start, end, newText);
                    start = end;
                    end = (start + qtdCaracteres);
                }
            } else if (end > text.toCharArray().length) {

                newText += justify(getTextLine(text, start, text.toCharArray().length), qtdCaracteres);

                break;
            }
            count++;
        }
        return newText;
    }

    private String getNewText(String text, int qtdCaracteres, int start, int end, String newText) {

        newText += justify(getTextLine(text, start, end), qtdCaracteres);
        newText += "\n";
        return newText;
    }

    private boolean isChacarcter(int character, int space, int comma, int newLine) {

        return character == space || character == comma || character == newLine;
    }

    private String[] getTextLine(String text, int positionInicial, int positionFinal) {

        return text.substring(positionInicial, positionFinal).split(" ");
    }

    public String justify(String[] words, int max) {

        String newText = "";
        int qtdWords = words.length;
        int qtdCharacter = 0;
        qtdCharacter = getQtdCharacter(words, qtdCharacter);
        int qtdSpaceCompleteLine = getQtdSpaceCompleteLine(max, qtdCharacter);
        int qtdSpaceBeetweenWords = getQtdSpaceBeetweenWords(qtdSpaceCompleteLine, (qtdWords - 1));
        int restQtdSpaceBeetweenWords = getRestQtdSpaceBeetweenWords(qtdSpaceBeetweenWords, (qtdWords - 1));

        int count = 0;
        newText = justifyText(words, newText, qtdWords, qtdSpaceCompleteLine, qtdSpaceBeetweenWords, count);
        return newText;
    }

    private String justifyText(String[] words, String newText, int qtdWords, int qtdSpaceCompleteLine, int qtdSpaceBeetweenWords, int count) {

        while (count < (qtdWords - 1)) {
            qtdSpaceCompleteLine = addSpaceCompleteLine(words, qtdSpaceCompleteLine, qtdSpaceBeetweenWords, count);
            count++;
            if (qtdSpaceCompleteLine == 0) {
                newText = getText(words, newText);
                break;
            } else if (count == (qtdWords - 1)) {
                count = 0;
                qtdSpaceBeetweenWords = 1;
            }
        }
        return newText;
    }

    private int addSpaceCompleteLine(String[] words, int qtdSpaceCompleteLine, int qtdSpaceBeetweenWords, int count) {
        for (int j = 0; j < qtdSpaceBeetweenWords; j++) {
            words[count] += " ";
            qtdSpaceCompleteLine--;
            if (qtdSpaceCompleteLine == 0) {
                break;
            }
        }
        return qtdSpaceCompleteLine;
    }

    private int getQtdSpaceCompleteLine(int max, int qtdCharacter) {
        return Math.abs(max - qtdCharacter);
    }

    private int getQtdSpaceBeetweenWords(int max, int qtdWords) {
        return Math.abs(max / qtdWords);
    }

    private int getRestQtdSpaceBeetweenWords(int max, int qtdWords) {
        return Math.abs(max % qtdWords);
    }

    private int getQtdCharacter(String[] words, int qtdCharacter) {
        for (String item : words) {
            qtdCharacter += item.toCharArray().length;
        }
        return qtdCharacter;
    }

    private String getText(String[] words, String newText) {
        for (String item : words) {
            newText += item;
        }
        return newText;
    }

}
