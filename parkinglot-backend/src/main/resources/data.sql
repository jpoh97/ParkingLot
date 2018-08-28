INSERT INTO Vehicle_Type (name, total_Places, creation_Date) VALUES
  ('CAR', 20, {ts '2016-11-09T11:44:44.797'}),
  ('MOTORCYCLE', 10, {ts '2016-11-09T11:44:44.797'});

INSERT INTO Parking_Rates (vehicle_Type_id, hour_Price, day_Price, extra_Price, creation_Date, active) VALUES
  (1, 1000, 8000, 0, {ts '2016-11-09T11:44:44.797'}, true),
  (2, 500, 4000, 1500, {ts '2016-11-09T11:44:44.797'}, false),
  (2, 500, 4000, 2000, {ts '2016-11-09T11:44:44.797'}, true),
  (2, 500, 4000, 0, {ts '2016-11-09T11:44:44.797'}, true);