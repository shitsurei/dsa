package DroneCrossPhotograph.util;

public class IOUtil {
    public enum MessageType {
        ERROR, INFO, SPLICT, COLLECTION
    }

    public static void outPutMatrix(boolean[][] collection, MessageType messageType, Integer... param) {
        int[][] collectionOutput = new int[collection.length][collection[0].length];
        switch (messageType) {
            case COLLECTION:
                collectionOutput[param[0]][param[1]] = 2;
                for (int i = 0; i < collection.length; i++) {
                    for (int j = 0; j < collection[0].length; j++) {
                        if (collection[i][j] && collectionOutput[i][j] != 2) {
                            collectionOutput[i][j] = 1;
                        }
                        System.out.print(collectionOutput[i][j] + "\t");
                    }
                    System.out.println();
                }
                break;
        }
    }

    public static void outPutMessage(String message, MessageType messageType) {
        StringBuilder stringBuilder = new StringBuilder();
        switch (messageType) {
            case INFO:
                stringBuilder.append("[info] ");
                break;
            case ERROR:
                stringBuilder.append("[error] ");
                break;
            case SPLICT:
                break;
        }
        stringBuilder.append(message);
        System.out.println(stringBuilder);
    }
}
