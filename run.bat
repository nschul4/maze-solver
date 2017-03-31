REM windows batch file

java ^
-cp "./target/maze-solver-0.0.1-SNAPSHOT-distro/maze-solver-0.0.1-SNAPSHOT/lib/*" ^
maze.solver.Main %* ^
;

pause
