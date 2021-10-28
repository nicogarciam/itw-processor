package fit.iterway.processor.dtos.obd;

//https://en.wikipedia.org/wiki/OBD-II_PIDs#Fuel_Type_Coding
public enum FuelType {

  GASOLINE(1),
  METHANOL(2),
  ETHANOL(3),
  DIESEL(4),
  PROPANE(7),
  ELECTRIC(8),
  BIFUEL_RUNNING_GASOLINE(9),
  BIFUEL_RUNNING_METHANOL(10),
  BIFUEL_RUNNING_ETHANOL(11),
  BIFUEL_RUNNING_LPG(12),
  BIFUEL_RUNNING_CNG(13),
  BIFUEL_RUNNING_PROPANE(14),
  BIFUEL_RUNNING_ELECTRICITY(15),
  HYBRID_GASOLINE(17),
  HYBRID_ETHANOL(18),
  HYBRID_DIESEL(19),
  HYBRID_ELECTRIC(20),
  HYBRID_RUNNING_ELECTRIC_COMBUSTION(21),
  HYBRID_REGENERATIVE(22),
  BIFUEL_RUNNING_DIESEL(23);

  final int code;


  FuelType(int value) {
    this.code = value;
  }


  public static FuelType getByCode(final int code) {

    for (FuelType type : FuelType.values()) {
      if (type.code == code) {
        return type;
      }
    }

    throw new IllegalArgumentException("Unknown FuelType Code: " + code);
  }
  /**
   * @return the code.
   */
  public int getCode() {
    return code;
  }

}
