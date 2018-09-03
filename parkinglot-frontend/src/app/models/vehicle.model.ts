
interface Vehicle {
    licensePlate: string;
    cylinderCapacity: number;
    vehicleTypeName: string;
  }

  
class VehcileClass implements Vehicle {
  licensePlate: string;  
  cylinderCapacity: number;
  vehicleTypeName: string;
  constructor(licensePlate: string,  cylinderCapacity: number, vehicleTypeName: string) { 
    this.licensePlate = licensePlate;  
    this.cylinderCapacity = cylinderCapacity;
    this.vehicleTypeName = vehicleTypeName;
  }
}

export { Vehicle, VehcileClass }