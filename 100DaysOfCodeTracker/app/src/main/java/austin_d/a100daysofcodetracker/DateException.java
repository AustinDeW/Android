package austin_d.a100daysofcodetracker;

class DateException extends Exception
{
    private static String message = "";

    DateException(String msg)
    {
        message = msg;
    }

    static String dateFormatException()
    {
        return message = "Date is formatted wrong. Format: MM/dd/yy\nExample: 01/03/17";
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
