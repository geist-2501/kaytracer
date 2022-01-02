class HittableList() : Hittable() {

    val objs : MutableList<Hittable> = mutableListOf()

    constructor(obj: Hittable) : this() {
        objs.add(obj)
    }

    fun add(obj: Hittable) {
        objs.add(obj)
    }

    fun clear() {
        objs.clear()
    }

    override fun hit(r: Ray, min: Double, max: Double): Hit? {
        var closest = max
        var bestHit: Hit? = null

        for (obj in objs) {
            val hit = obj.hit(r, min, closest)
            if (hit != null) {
                closest = hit.t
                bestHit = hit
            }
        }

        return bestHit
    }


}