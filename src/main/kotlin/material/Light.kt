package material

import model.HitRecord
import model.Ray
import utils.Vec3

class Light(attenuation: Vec3, override val emitted: Vec3) : Material by Lambertian(attenuation)