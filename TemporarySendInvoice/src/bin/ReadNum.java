package bin;

public final class ReadNum {
    public static String read(String num) {
        int offset = num.length() % 3;
        if (offset == 1) {
            num = "00" + num;
        } else if (offset == 2) {
            num = "0" + num;
        }
        StringBuilder sb = new StringBuilder();
        int index = num.length() - 1,
                counter = 0;
        while (index > 0) {
            String thisTriplet = readTriple(num.charAt(index), num.charAt(index - 1), num.charAt(index - 2), sb.length() != 0);
            if (thisTriplet.length() != 0) sb.insert(0, tripletRank(counter)).insert(0, thisTriplet);
            index -= 3;
            counter++;
        }
        return sb.toString();
    }

    private static String readTriple(char first, char second, char third, boolean isNotEmpty) {
        StringBuilder sb = new StringBuilder();
        //read first digit
        sb.append(readSingleNum(first));
        //read second digit
        if (second == '0' && (first != '0' && third != '0' || isNotEmpty && first != '0')) sb.insert(0, "linh ");
        else if (second != '0') sb.insert(0, "mươi ").insert(0, readSingleNum(second));
        //read third digit
        if (third == '0' && isNotEmpty && (first != '0' || second != '0')) sb.insert(0, "trăm ").insert(0, "không");
        else if (third != '0') sb.insert(0, "trăm ").insert(0, readSingleNum(third));
        return sb.toString();
    }

    private static String readSingleNum(char num) {
        return switch (num) {
            case '1' -> "một ";
            case '2' -> "hai ";
            case '3' -> "ba ";
            case '4' -> "bốn ";
            case '5' -> "năm ";
            case '6' -> "sáu ";
            case '7' -> "bẩy ";
            case '8' -> "tám ";
            case '9' -> "chín ";
            default -> "";
        };
    }

    private static String tripletRank(int counter) {
        return switch (counter) {
            case 1 -> "nghìn ";
            case 2 -> "triệu ";
            case 3 -> "tỉ ";
            default -> "";
        };
    }
}
