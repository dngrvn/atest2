
import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;


public class RafflePrizes {
    private List<Toy> _toys = new ArrayList<>();
    private List<Toy> _prizeToys = new ArrayList<>();
    private Random _random = new Random();

    public Toy rollPrize(){
        if (_toys.size()==0)
        {
            System.out.println("Игрушек в розыгрыше нет");
            return null;
        }
        var percent = _random.nextInt(100);
        var percents = _toys.stream().map(it->it.getPercent()).collect(Collectors.toList());
        if (percents.size()==0)
        {
            System.out.println("Похоже все игрушки закончились");
            return null;
        }
        var closest = getClosest(percents,percent);
        var result = _toys.stream().filter(it->it.getPercent()==closest).findFirst().orElse(null);
        if (result==null) {
            System.out.println("Похоже все игрушки закончились");
            return null;
        }
        if (result.getCount()>1)
            result.setCount(result.getCount()-1);
        else
            _toys.remove(result);
        var existingPrize=_prizeToys.stream().filter(it->it.getId()==result.getId()).findFirst().orElse(null);
        if (existingPrize==null)
            _prizeToys.add(new Toy(result.getName(),result.getCount(),result.getPercent()));
        else
            existingPrize.setCount(existingPrize.getCount()+1);
        return  result;
    }

    public Toy getPrize(UUID id){
        var result = _prizeToys.stream().filter(it->it.getId()==id).findFirst().orElse(null);
        if (result==null) {
            System.out.println("Игрушка с идентификатором " + id + " не найдена");
            return null;
        }
        if (result.getCount()==1)
            _prizeToys.remove(result);
        else
            result.setCount(result.getCount()-1);
        writeToFile(java.util.UUID.randomUUID().toString(),result.getName());
        return result;
    }

    public Toy getPrize(){
        var result = _prizeToys.stream().findFirst().orElse(null);
        if (result==null) {
            System.out.println("Похоже все игрушки закончились");
            return null;
        }

        if (result.getCount()==1)
            _prizeToys.remove(result);
        else
            result.setCount(result.getCount()-1);
        writeToFile(java.util.UUID.randomUUID().toString(),result.getName());
        return result;
    }

    public void addToy(Toy toy){
        _toys.add(toy);
    }

    public void addToy(String name){
        _toys.add(new Toy(name,_random.nextInt(2)+1,_random.nextInt(100)));
    }

    public void fillToys(Integer maxCount){
        List<String> names = new ArrayList<>();
        names.add("Медведь маленький");
        names.add("Паровозик");
        names.add("Медведь большой");
        names.add("Тигр маленький");
        names.add("Тигр большой");
        names.add("Самолет");
        names.add("Конструктор");
        names.add("Набор шариков");
        names.add("Набор разноцветных ручек");
        names.add("Кукла маленькая");
        names.add("Кукла большая");
        names.add("Игрушка щенок");
        names.forEach((name)->_toys.add(new Toy(name,_random.nextInt(maxCount)+1,_random.nextInt(100))));
    }

    private  Integer getClosest(List<Integer> sortedList, Integer key) {
        Integer index = Collections.binarySearch(sortedList, key);
        Integer closest;
        if (index >= 0) {
            closest = sortedList.get(index);
        } else {
            index = -index - 1;
            if (index == 0){
                closest = sortedList.get(index);
            } else if (index == sortedList.size()){
                closest = sortedList.get(index - 1);
            } else {
                Integer prev = sortedList.get(index - 1);
                Integer next = sortedList.get(index);
                closest = ((key - prev) < (next - key)) ? prev : next;
            }
        }
        return closest;
    }

    private static void writeToFile(String path,String value)   {
        try
        {
            FileOutputStream outputStream = new FileOutputStream(path);
            byte[] strToBytes = value.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        }
        catch (Exception ex){
            System.out.println("Не удалось записать в файл "+path);
        }
    }
}
