import java.util.Random;

public class Barrier {

    public Vector2 Start;
    public Vector2 End;

    private Random rand;

    public Barrier(Vector2 start, Vector2 end) {
        this.Start = start;
        this.End = end;

        rand = new Random();
    }

    public Vector2 CalculateTurnOnBarrier(Bird bird) {
        Vector2 calculateTurn = new Vector2(0, 0);

        if (bird.X > Start.x - Settings.BarrierDistance && bird.X < End.x + Settings.BarrierDistance) {
            if (bird.Y > Start.y - Settings.BarrierDistance && bird.Y < End.y + Settings.BarrierDistance) {

                if (bird.X < End.x)
                    calculateTurn.x -= Settings.BarrierPower;
                if (bird.X > Start.x)
                    calculateTurn.x += Settings.BarrierPower;
                if (bird.Y < End.y)
                    calculateTurn.y -= Settings.BarrierPower;
                if (bird.Y > Start.y)
                    calculateTurn.y += Settings.BarrierPower;
            }
        }

        return calculateTurn;
    }

}