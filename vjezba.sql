CREATE PROCEDURE ObrisiDrzaveOdId
    @MinimalniId INT
AS
BEGIN
    DELETE FROM dbo.drzava
    WHERE IDDrzava >= @MinimalniId;
END;