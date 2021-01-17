package com.company;

public class Mu extends Subject {
    {
        this.setName("Mu");
        System.out.println(getName()+" was created");
    }

    void Awake(FishHook hook) {
        if (ExtendMath.checkrandom(getWaveLevel())) {
            System.out.println(getName()+" woke up because of the storm and "+hook.Move(Action.HOVER,Position.OVERHER));
        } else {
            System.out.println(getName()+" didn't even wake up when "+hook.Move(Action.HOVER,Position.OVERHER));
        }
    }
}
