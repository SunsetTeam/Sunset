//dust
const dust_copter = extend(UnitType, "dust_copter", {
	draw(unit) {
		this.super$draw(unit);
		Draw.rect(
			"azaria-dust_copter-rotor",
			unit.x + Angles.trnsx(unit.rotation - 90, 0, 0),
			unit.y + Angles.trnsy(unit.rotation - 90, 0, 0),
			Time.time * -15);
		Draw.rect(
			"azaria-dust_copter-rotor-outline",
			unit.x + Angles.trnsx(unit.rotation - 90, 0, 0),
			unit.y + Angles.trnsy(unit.rotation - 90, 0, 0),
			Time.time * -15);
	}
});
//fures
const fures_copter = extend(UnitType, "fures_copter", {
	draw(unit) {
		this.super$draw(unit);
		Draw.rect(
			"azaria-fures_copter-rotor",
			unit.x + Angles.trnsx(unit.rotation - 90, 0, 0),
			unit.y + Angles.trnsy(unit.rotation - 90, 0, 0),
			Time.time * -15);
		Draw.rect(
			"azaria-fures_copter-rotor-outline",
			unit.x + Angles.trnsx(unit.rotation - 90, 0, 0),
			unit.y + Angles.trnsy(unit.rotation - 90, 0, 0),
			Time.time * -15);
	}
});
//guardian
const guardian_copter = extend(UnitType, "guardian_copter", {
	draw(unit) {
		this.super$draw(unit);
		Draw.rect(
			"azaria-guardian_copter-rotor",
			unit.x + Angles.trnsx(unit.rotation - 90, 0, 0),
			unit.y + Angles.trnsy(unit.rotation - 90, 0, 0),
			Time.time * -15);
		Draw.rect(
			"azaria-guardian_copter-rotor-outline",
			unit.x + Angles.trnsx(unit.rotation - 90, 0, 0),
			unit.y + Angles.trnsy(unit.rotation - 90, 0, 0),
			Time.time * -15);
	}
});
//shielder
const shielder_copter = extend(UnitType, "shielder_copter", {
	draw(unit) {
		this.super$draw(unit);
		Draw.rect(
			"azaria-shielder_copter-rotor",
			unit.x + Angles.trnsx(unit.rotation - 90, 0, 0),
			unit.y + Angles.trnsy(unit.rotation - 90, 0, 0),
			Time.time * -15);
		Draw.rect(
			"azaria-shielder_copter-rotor-outline",
			unit.x + Angles.trnsx(unit.rotation - 90, 0, 0),
			unit.y + Angles.trnsy(unit.rotation - 90, 0, 0),
			Time.time * -15);
	}
});
//shyreng
const shyreng_copter = extend(UnitType, "shyreng_copter", {
	draw(unit) {
		this.super$draw(unit);
		Draw.rect(
			"azaria-shyreng_copter-rotor",
			unit.x + Angles.trnsx(unit.rotation - 90, 0, 0),
			unit.y + Angles.trnsy(unit.rotation - 90, 0, 0),
			Time.time * -15);
		Draw.rect(
			"azaria-shyreng_copter-rotor-outline",
			unit.x + Angles.trnsx(unit.rotation - 90, 0, 0),
			unit.y + Angles.trnsy(unit.rotation - 90, 0, 0),
			Time.time * -15);
	}
});
dust_copter.constructor = () => extend(UnitEntity, {});
fures_copter.constructor = () => extend(UnitEntity, {});
guardian_copter.constructor = () => extend(UnitEntity, {});
shielder_copter.constructor = () => extend(UnitEntity, {});
shyreng_copter.constructor = () => extend(UnitEntity, {});
