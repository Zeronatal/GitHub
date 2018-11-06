import core.*;
import core.Camera;

public class RoadController
{
    public static Double passengerCarMaxWeight = 3500.0; // kg  максимальны вес легкового автомобиля
    public static Integer passengerCarMaxHeight = 2000; // mm   максимальная высота легкового автомобиля
    public static Integer controllerMaxHeight = 3500; // mm     максисальная высота транспорта

    public static Integer passengerCarPrice = 100; // RUB       стоимость проезда легкового автомобиля
    public static Integer cargoCarPrice = 250; // RUB           cтоимость проезда грузового автомобиля
    public static Integer vehicleAdditionalPrice = 200; // RUB  стоимость проезда прицепа

    public static Integer maxOncomingSpeed = 60; // km/h        максимальная скорость въезда
    public static Integer speedFineGrade = 20; // km/h          градация превышения скорости
    public static Integer finePerGrade = 500; // RUB            штраф за минимальное превышение скорости
    public static Integer criminalSpeed = 160; // km/h          криминальная скорость

    public static void main(String[] args)
    {
        for(Integer i = 0; i < 10; i++)        //               определяем начальную переменную цикла
        {
            Car car = Camera.getNextCar();
            System.out.println(car);
            System.out.println("Скорость: " + Camera.getCarSpeed(car) + " км/ч");



            /**
             * Проверка на наличие номера в списке номеров нарушителей
             */
            Boolean policeCalled = true;
            for(String criminalNumber : Police.getCriminalNumbers())
            {
                String carNumber = car.getNumber();    // определяем номер автомобиля (получаем из другого класса)
                if(carNumber.equals(criminalNumber)) {
                    Police.call("автомобиль нарушителя с номером " + carNumber);
                    blockWay("не двигайтесь с места! За вами уже выехали!");
                    break;
                }
            }
            if(Police.wasCalled()) {
                continue;
            }

            /**
             * Проверяем высоту и массу автомобиля, вычисляем стоимость проезда
             */
            Integer carHeight = car.getHeight();   // определение высоты автомобиля
            Integer price = 0;
            if(carHeight > controllerMaxHeight)     // объявляем переменную стоимость проезда и инициализируем
            {
                blockWay("высота вашего ТС превышает высоту пропускного пункта!");
                continue;
            }
            else if(carHeight > passengerCarMaxHeight)
            {
                Double weight = WeightMeter.getWeight(car);
                //Грузовой автомобиль
                if(weight > passengerCarMaxWeight)
                {
                    price = cargoCarPrice;     // стоимость проезда равна стоимости проезда (легкового - было) грузового автомобиля
                    if(car.hasVehicle()) {
                        price = price + vehicleAdditionalPrice;   // стоимость проезда автомобиля с прицепом
                    }
                }
                //Легковой автомобиль
                else {
                    price = passengerCarPrice;
                }
            }
            else {
                price = passengerCarPrice;
            }
            /**
             * Пропускаем автомобили спецтранспорта
             */
            if(car.isSpecial()) {
                openWay();
                continue;
            }
            /**
             * Проверка скорости подъезда и выставление штрафа
             */
            Integer carSpeed = Camera.getCarSpeed(car);    // определение скорости автомобиля
            if(carSpeed > criminalSpeed)
            {
                Police.call("cкорость автомобиля - " + carSpeed + " км/ч, номер - " + car.getNumber());
                blockWay("вы значительно превысили скорость. Ожидайте полицию!");
                continue;
            }
            else if(carSpeed > maxOncomingSpeed)
            {
                Integer overSpeed = carSpeed - maxOncomingSpeed;         //величина превышения скорости
                Integer totalFine = finePerGrade * (1 + overSpeed / speedFineGrade);    // итоговая сумма штрафа
                System.out.println("Вы превысили скорость! Штраф: " + totalFine + " руб.");
                price = price + totalFine;
            }

            /**
             * Отображение суммы к оплате
             */
            System.out.println("Общая сумма к оплате: " + price + " руб.");
        }

    }

    /**
     * Открытие шлагбаума
     */
    public static void openWay()
    {
        System.out.println("Шлагбаум открывается... Счастливого пути!");
    }

    public static void blockWay(String reason)
    {
        System.out.println("Проезд невозможен: " + reason);
    }
}