package org.bg.test.sensors;

import com.opencsv.bean.CsvBindByPosition;

public abstract class CsvBean {

    @CsvBindByPosition(position = 0)
    protected long millis;

    public long getMillis() {
        return millis;
    }

    @Override
    public String toString() {
        return "millis=" + millis;
    }
}

//    protected long millis;
//
//    public AbstractTarget(HashMap<String, String> keyValue) {
//        HashMap<String, Method> nameToMethod = new HashMap<>();
//        Arrays.stream(this.getClass().getDeclaredMethods()).forEach(method -> {
//            nameToMethod.put(method.getName(), method);
//        });
//        // do your magic!
//        keyValue.entrySet().stream().forEach(stringStringEntry -> {
//            try {
//                nameToMethod.get("set"+stringStringEntry.getKey().toLowerCase(Locale.ROOT)).invoke(this, stringStringEntry.getValue());
//            } catch (InvocationTargetException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        });
//        initializeKeyFields();
//    }
//
//    public abstract void initializeKeyFields();
//
//    public long getMillis() {
//        return millis;
//    }
