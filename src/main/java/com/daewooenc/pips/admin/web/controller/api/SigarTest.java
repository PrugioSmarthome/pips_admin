/*
package com.daewooenc.pips.admin.web.controller.api;

import org.hyperic.sigar.CpuPerc;

import org.hyperic.sigar.Sigar;

import org.hyperic.sigar.SigarException;



public class SigarTest{

    public static void main(String[] args){

        // sigar 객체 생성
        Sigar sigar = new Sigar();
        CpuPerc cpu = null;
        CpuPerc[] cpus = null;

        try{
            cpu = sigar.getCpuPerc();
            cpus = sigar.getCpuPercList();

        }catch (SigarException e){
            System.out.println("Error : "+e);
        }

        //cpu사용량 출력
        System.out.println("Total cpu----");

        cpu_output(cpu);

        for(int i=0; i < cpus.length; i++)
        {
            System.out.println("cpu"+i+"----");
            cpu_output(cpus[i]);
        }

    }


    public static void cpu_output(CpuPerc cpu){

        System.out.println("User Time\t :"+CpuPerc.format(cpu.getUser()));
        System.out.println("Sys Time\t :"+CpuPerc.format(cpu.getSys()));
        System.out.println("Idle Time\t :"+CpuPerc.format(cpu.getSys()));
    }

}
*/
