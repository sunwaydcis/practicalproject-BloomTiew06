package ch.makery.address.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateUtil:
  val DATE_PATTERN = "dd.MM.yyyy"
  val DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN)
  
  extension (date: LocalDate)
    def asString: String =
      if (date == null)
        return null
      return DATE_FORMATTER.format(date)

  extension (data: String)
    def parseLocalDate: LocalDate =
      try
        LocalDate.parse(data, DATE_FORMATTER)
      catch
        case e: DateTimeParseException => null
        
    def isValid: Boolean =
      data.parseLocalDate != null


class DateUtil {

}
